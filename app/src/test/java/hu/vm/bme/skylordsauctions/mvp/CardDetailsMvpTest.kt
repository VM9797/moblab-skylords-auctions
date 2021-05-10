package hu.vm.bme.skylordsauctions.mvp

import hu.vm.bme.skylordsauctions.carddetails.CardDetailsInteractor
import hu.vm.bme.skylordsauctions.carddetails.CardDetailsPresenter
import hu.vm.bme.skylordsauctions.carddetails.CardDetailsView
import hu.vm.bme.skylordsauctions.data.MockCards
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

class CardDetailsMvpTest {

    @Test
    fun interactorShouldReturnTheCorrectCard() {
        val mockCardService = mock(CardService::class.java)
        val interactor = CardDetailsInteractor(mockCardService)
        val cardList = listOf(MockCards.abomination)

        runBlocking {
            `when`(mockCardService.getDisplayableCards()).thenReturn(cardList)

            assertEquals(cardList.first().imageName, interactor.getCardByName("Abomination [B]").imageName)
        }
    }

    @Test
    fun interactorShouldThrowAnExceptionWhenCardIsNotFound() {
        val mockCardService = mock(CardService::class.java)
        val interactor = CardDetailsInteractor(mockCardService)
        val cardList = listOf(MockCards.abomination)

        runBlocking {
            `when`(mockCardService.getDisplayableCards()).thenReturn(cardList)

            assertThrows(NoSuchElementException::class.java) {
                runBlocking {
                    interactor.getCardByName("Not existing card")
                }
            }
        }
    }

    @Test
    fun presenterShouldInvokeView() {
        Dispatchers.setMain(newSingleThreadContext("UI thread"))

        val interactor = mock(CardDetailsInteractor::class.java)
        val view = mock(CardDetailsView::class.java)

        val presenter = CardDetailsPresenter(interactor)
        presenter.attachView(view)

        val card = MockCards.abomination

        runBlocking {
            `when`(interactor.getCardByName("Abomination [B]")).thenReturn(card)
            val job = presenter.getCard("Abomination [B]")
            job.join()

            assertEquals(1, mockingDetails(view).invocations.size)
        }

        Dispatchers.resetMain()

    }
}