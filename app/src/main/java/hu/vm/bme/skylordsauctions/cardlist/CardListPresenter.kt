package hu.vm.bme.skylordsauctions.cardlist

import hu.vm.bme.skylordsauctions.mvp.Presenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardListPresenter @Inject constructor(private val cardListInteractor: CardListInteractor): Presenter<CardListView>() {

    fun loadAllCards() = GlobalScope.launch(Dispatchers.IO) {
        val cards = cardListInteractor.getAllCards()

        GlobalScope.launch(Dispatchers.Main) {
            view?.displayCards(cards)
        }.join()
    }
}