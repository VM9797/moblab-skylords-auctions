package hu.vm.bme.skylordsauctions.core

import hu.vm.bme.skylordsauctions.DaoMock
import hu.vm.bme.skylordsauctions.assertCollectionEquals
import hu.vm.bme.skylordsauctions.data.*
import hu.vm.bme.skylordsauctions.network.cardbase.CardbaseApi
import hu.vm.bme.skylordsauctions.network.smj.SmjApi
import hu.vm.bme.skylordsauctions.service.CardService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CardServiceTest {
    private lateinit var cardService: CardService
    private lateinit var smjMock: SmjApi
    private lateinit var cardbaseMock: CardbaseApi
    private lateinit var daoMock: AppDaoProvider

    @Before
    fun setup() {
        cardbaseMock = mock(CardbaseApi::class.java)
        smjMock = mock(SmjApi::class.java)
        daoMock = mock(AppDaoProvider::class.java)

        cardService = CardService(cardbaseMock, smjMock, daoMock)
    }


    @Test
    fun cardsShouldBeCached() {
        runBlocking {
            `when`(daoMock.cardNameIdMappingDao()).thenReturn(DaoMock())
            `when`(cardbaseMock.getCardList()).thenReturn(MockCardbaseResult.result)
            cardService.getDisplayableCards()
            cardService.getDisplayableCards()

            assertEquals(1, mockingDetails(cardbaseMock).invocations.size)
        }
    }

    @Test
    fun smjCardNamesShouldBeSet() {
        runBlocking {
            `when`(cardbaseMock.getCardList()).thenReturn(MockCardbaseResult.result)
            `when`(daoMock.cardNameIdMappingDao()).thenReturn(DaoMock())

            val cards = cardService.getDisplayableCards()

            var exception: Exception? = null

            for (card in cards) {
                try {
                    cardService.getCardSmjIdPairByCardName(card.imageName)
                } catch (e: Exception) {
                    exception = e
                }
            }

            assertNull(exception)
        }
    }

    @Test
    fun smjCardNamesShouldBeCorrect() {
        runBlocking {
            `when`(cardbaseMock.getCardList()).thenReturn(MockCardbaseResult.result)
            `when`(daoMock.cardNameIdMappingDao()).thenReturn(DaoMock())

            val smjIds = cardService.getDisplayableCards()
                .map { cardService.getCardSmjIdPairByCardName(it.imageName) }
                .map { it.second }

            val expectedSmjIds = listOf("abomination-b", "amii-monument", "shaman", "protector's-seal-ls", "banditos-g")

            assertCollectionEquals(expectedSmjIds, smjIds)
        }
    }

    @Test
    fun noteworthyPricesShouldBeFetchedByCardName() {
        val cardName = "Abomination [B]"
        runBlocking {
            `when`(cardbaseMock.getCardList()).thenReturn(MockCardbaseResult.result)
            `when`(smjMock.getNoteworthyPricesForCard(cardName)).thenReturn(listOf(MockNoteworthyPrices.abominationPrices))
            `when`(daoMock.cardNameIdMappingDao()).thenReturn(DaoMock())

            val noteworthyPrices = cardService.getNoteworthyPricesForCard(cardName)

            assertEquals(MockCardNameMappings.abominationMapping.smjCardId, noteworthyPrices._id)
        }
    }

    @Test
    fun priceHistoryShouldBeFetchedForCard() {
        val cardName = "Abomination [B]"
        runBlocking {
            `when`(cardbaseMock.getCardList()).thenReturn(MockCardbaseResult.result)
            `when`(smjMock.getPriceHistoryForCard(cardName)).thenReturn(MockPriceHistory.abominationPriceHistory)
            `when`(daoMock.cardNameIdMappingDao()).thenReturn(DaoMock())

            val history = cardService.getAuctionHistoryForCard(cardName)
            assertEquals(MockCardNameMappings.abominationMapping.smjCardId, history.id)
        }
    }

    @Test
    fun getMappingPairShouldThrowWhenInvalidCardNameIsProvided() {
        runBlocking {
            `when`(daoMock.cardNameIdMappingDao()).thenReturn(DaoMock())

            assertThrows(RuntimeException::class.java) {
                runBlocking {
                    cardService.getCardSmjIdPairByCardName("Invalid card name")
                }
            }
        }
    }
}