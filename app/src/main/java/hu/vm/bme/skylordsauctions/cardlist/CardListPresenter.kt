package hu.vm.bme.skylordsauctions.cardlist

import android.util.Log
import hu.vm.bme.skylordsauctions.mvp.Presenter
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CardListPresenter @Inject constructor(private val cardListInteractor: CardListInteractor): Presenter<CardListView>() {

    fun getNoteworthyPricesForCard(card: Card) = GlobalScope.launch(Dispatchers.IO) {
        val prices = cardListInteractor.getNoteworthyPricesForCard(card)
        view?.logPriceInfo(prices)
    }

    fun loadAllCards() = GlobalScope.launch(Dispatchers.IO) {
        val cards = cardListInteractor.getAllCards()
        view?.displayCardInfo(cards.random())
    }
}