package hu.vm.bme.skylordsauctions.mvp

import hu.vm.bme.skylordsauctions.assertCollectionEquals
import hu.vm.bme.skylordsauctions.auctions.AuctionDetails
import hu.vm.bme.skylordsauctions.auctions.AuctionsInteractor
import hu.vm.bme.skylordsauctions.auctions.AuctionsPresenter
import hu.vm.bme.skylordsauctions.auctions.AuctionsView
import hu.vm.bme.skylordsauctions.data.MockCardNameMappings
import hu.vm.bme.skylordsauctions.data.MockCards
import hu.vm.bme.skylordsauctions.data.MockNoteworthyPrices
import hu.vm.bme.skylordsauctions.data.MockPriceHistory
import hu.vm.bme.skylordsauctions.service.CardService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.Mockito.*

class AuctionsMvpTest {
    val cardName = "Abomination [B]"

    @Test
    fun interactorShouldThrowExceptionIfCardDoesNotExist() {
        val mockCardService = mock(CardService::class.java)
        val interactor = AuctionsInteractor(mockCardService)

        runBlocking {
            `when`(mockCardService.getCardSmjIdPairByCardName("Invalid card name")).thenThrow(NoSuchElementException::class.java)

            assertThrows(NoSuchElementException::class.java) {
                runBlocking { interactor.getAuctionDetails("Invalid card name") }
            }
        }
    }

    @Test
    fun interactorShouldGroupHistoryByDay() {
        val mockCardService = mock(CardService::class.java)
        val interactor = AuctionsInteractor(mockCardService)
        val smjId = MockCardNameMappings.abominationMappingPair.second

        runBlocking {
            `when`(mockCardService.getCardSmjIdPairByCardName(cardName)).thenReturn(MockCardNameMappings.abominationMappingPair)
            `when`(mockCardService.getNoteworthyPricesForCard(smjId)).thenReturn(MockNoteworthyPrices.abominationPrices)
            `when`(mockCardService.getAuctionHistoryForCard(smjId)).thenReturn(MockPriceHistory.abominationPriceHistory)

            val auctionDetails = interactor.getAuctionDetails(cardName)

            assertEquals(2, auctionDetails.chartData.size)
        }
    }

    @Test
    fun interactorShouldReturnTheLowestPriceDataOfDay() {
        val mockCardService = mock(CardService::class.java)
        val interactor = AuctionsInteractor(mockCardService)
        val smjId = MockCardNameMappings.abominationMappingPair.second

        runBlocking {
            `when`(mockCardService.getCardSmjIdPairByCardName(cardName)).thenReturn(MockCardNameMappings.abominationMappingPair)
            `when`(mockCardService.getNoteworthyPricesForCard(smjId)).thenReturn(
                MockNoteworthyPrices.abominationPrices
            )
            `when`(mockCardService.getAuctionHistoryForCard(smjId)).thenReturn(MockPriceHistory.abominationPriceHistory)

            val auctionDetails = interactor.getAuctionDetails(cardName)

            assertCollectionEquals(listOf(20f, 60f), auctionDetails.chartData.map { it.y })
        }
    }

    @Test
    fun presenterShouldInvokeView() {
        Dispatchers.setMain(newSingleThreadContext("UI thread"))

        val interactor = mock(AuctionsInteractor::class.java)
        val view = mock(AuctionsView::class.java)

        val presenter = AuctionsPresenter(interactor)
        presenter.attachView(view)

        val card = MockCards.abomination

        runBlocking {
            `when`(interactor.getAuctionDetails(cardName)).thenReturn(
                AuctionDetails(
                    card,
                    MockNoteworthyPrices.abominationPrices,
                    emptyList()
                )
            )

            val job = presenter.loadAuctionDetails(cardName)
            job.join()

            assertEquals(1, mockingDetails(view).invocations.size)
        }

        Dispatchers.resetMain()

    }

    @Test
    fun presenterShouldNotInvokeViewIfNotSet() {
        Dispatchers.setMain(newSingleThreadContext("UI thread"))

        val interactor = mock(AuctionsInteractor::class.java)
        val view = mock(AuctionsView::class.java)

        val presenter = AuctionsPresenter(interactor)

        val card = MockCards.abomination

        runBlocking {
            `when`(interactor.getAuctionDetails(cardName)).thenReturn(
                AuctionDetails(
                    card,
                    MockNoteworthyPrices.abominationPrices,
                    emptyList()
                )
            )

            val job = presenter.loadAuctionDetails(cardName)
            job.join()

            assertEquals(0, mockingDetails(view).invocations.size)
        }

        Dispatchers.resetMain()

    }
}