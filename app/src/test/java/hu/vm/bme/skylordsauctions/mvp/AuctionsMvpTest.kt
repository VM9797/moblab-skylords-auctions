package hu.vm.bme.skylordsauctions.mvp

import hu.vm.bme.skylordsauctions.assertCollectionEquals
import hu.vm.bme.skylordsauctions.auctions.AuctionDetails
import hu.vm.bme.skylordsauctions.auctions.AuctionsInteractor
import hu.vm.bme.skylordsauctions.auctions.AuctionsPresenter
import hu.vm.bme.skylordsauctions.auctions.AuctionsView
import hu.vm.bme.skylordsauctions.data.MockCards
import hu.vm.bme.skylordsauctions.data.MockNoteworthyPrices
import hu.vm.bme.skylordsauctions.data.MockPriceHistory
import hu.vm.bme.skylordsauctions.service.CardService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*

class AuctionsMvpTest {
    val cardName = "abomination-b"

    @Test
    fun interactorShouldThrowExceptionIfCardDoesNotExist() {
        val mockCardService = mock(CardService::class.java)
        val interactor = AuctionsInteractor(mockCardService)

        runBlocking {
            `when`(mockCardService.getDisplayableCards()).thenReturn(listOf(MockCards.abomination.apply {
                smjId = cardName
            }))

            assertThrows(NoSuchElementException::class.java) {
                runBlocking { interactor.getAuctionDetails("Invalid card name") }
            }
        }
    }

    @Test
    fun interactorShouldGroupHistoryByDay() {
        val mockCardService = mock(CardService::class.java)
        val interactor = AuctionsInteractor(mockCardService)

        runBlocking {
            `when`(mockCardService.getDisplayableCards()).thenReturn(listOf(MockCards.abomination.apply {
                smjId = cardName
            }))
            `when`(mockCardService.getNoteworthyPricesForCard(cardName)).thenReturn(
                MockNoteworthyPrices.abominationPrices
            )
            `when`(mockCardService.getAuctionHistoryForCard(cardName)).thenReturn(MockPriceHistory.abominationPriceHistory)

            val auctionDetails = interactor.getAuctionDetails(cardName)

            assertEquals(2, auctionDetails.chartData.size)
        }
    }

    @Test
    fun interactorShouldReturnTheLowestPriceDataOfDay() {
        val mockCardService = mock(CardService::class.java)
        val interactor = AuctionsInteractor(mockCardService)

        runBlocking {
            `when`(mockCardService.getDisplayableCards()).thenReturn(listOf(MockCards.abomination.apply {
                smjId = cardName
            }))
            `when`(mockCardService.getNoteworthyPricesForCard(cardName)).thenReturn(
                MockNoteworthyPrices.abominationPrices
            )
            `when`(mockCardService.getAuctionHistoryForCard(cardName)).thenReturn(MockPriceHistory.abominationPriceHistory)

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