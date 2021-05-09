package hu.vm.bme.skylordsauctions.mvp

import hu.vm.bme.skylordsauctions.cardlist.CardListInteractor
import hu.vm.bme.skylordsauctions.cardlist.CardListPresenter
import hu.vm.bme.skylordsauctions.cardlist.CardListView
import hu.vm.bme.skylordsauctions.data.MockCards
import hu.vm.bme.skylordsauctions.service.CardService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*

class CardListMvpTest {

    @Test
    fun cardListInteractorShouldReturnTheCardList() {
        val cardService = mock(CardService::class.java)
        val cardListInteractor = CardListInteractor(cardService)
        val cardList = listOf(MockCards.abomination)

        runBlocking {
            `when`(cardService.getDisplayableCards()).thenReturn(cardList)

            assertEquals(cardList, cardListInteractor.getAllCards())
        }
    }

    @Test
    fun cardListPresenterShouldCallViewAndInteractor() {
        Dispatchers.setMain(newSingleThreadContext("UI thread"))

        val cardListInteractor = mock(CardListInteractor::class.java)
        val cardListView = mock(CardListView::class.java)

        val cardListPresenter = CardListPresenter(cardListInteractor)
        cardListPresenter.attachView(cardListView)

        val cardList = listOf(MockCards.abomination)

        runBlocking {
            `when`(cardListInteractor.getAllCards()).thenReturn(cardList)
            val job = cardListPresenter.loadAllCards()
            job.join()

            assertEquals(1, mockingDetails(cardListView).invocations.size)
        }

        Dispatchers.resetMain()

    }
}