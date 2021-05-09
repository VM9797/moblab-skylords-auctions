package hu.vm.bme.skylordsauctions.core

import hu.vm.bme.skylordsauctions.assertCollectionEquals
import hu.vm.bme.skylordsauctions.data.AppDaoProvider
import hu.vm.bme.skylordsauctions.data.MockCardbaseResult
import hu.vm.bme.skylordsauctions.data.MockNoteworthyPrices
import hu.vm.bme.skylordsauctions.data.MockPriceHistory
import hu.vm.bme.skylordsauctions.network.cardbase.CardbaseApi
import hu.vm.bme.skylordsauctions.network.smj.SmjApi
import hu.vm.bme.skylordsauctions.service.CardService
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
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

            val cards = cardService.getDisplayableCards()

            val anyCardWithoutSmjId = cards.any { !it.isSmjIdInitialized() }
            assert(!anyCardWithoutSmjId)
        }
    }

    @Test
    fun smjCardNamesShouldBeCorrect() {
        runBlocking {
            `when`(cardbaseMock.getCardList()).thenReturn(MockCardbaseResult.result)

            val smjCardNames = cardService.getDisplayableCards().map { it.smjId }

            assertCollectionEquals(listOf("abomination-b", "amii-monument", "shaman", "protector's-seal-ls", "banditos-g"), smjCardNames)
        }
    }

    @Test
    fun noteworthyPricesShouldBeFetchedByCardName() {
        val cardName = "abomination-b"
        runBlocking {
            `when`(cardbaseMock.getCardList()).thenReturn(MockCardbaseResult.result)
            `when`(smjMock.getNoteworthyPricesForCard(cardName)).thenReturn(listOf(MockNoteworthyPrices.abominationPrices))

            val noteworthyPrices = cardService.getNoteworthyPricesForCard(cardName)

            assertEquals(cardName, noteworthyPrices._id)
        }
    }

    @Test
    fun priceHistoryShouldBeFetchedForCard() {
        val cardName = "abomination-b"
        runBlocking {
            `when`(cardbaseMock.getCardList()).thenReturn(MockCardbaseResult.result)
            `when`(smjMock.getPriceHistoryForCard(cardName)).thenReturn(MockPriceHistory.abominationPriceHistory)

            val history = cardService.getAuctionHistoryForCard(cardName)

            assertEquals(cardName, history.id)
        }
    }
}