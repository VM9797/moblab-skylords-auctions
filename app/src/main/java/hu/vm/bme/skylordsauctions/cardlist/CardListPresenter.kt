package hu.vm.bme.skylordsauctions.cardlist

import android.content.Context
import android.content.Intent
import android.util.Log
import hu.vm.bme.skylordsauctions.carddetails.CardDetailsActivity
import hu.vm.bme.skylordsauctions.mvp.Presenter
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.util.CARD_DETAILS_CARD_NAME
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CardListPresenter @Inject constructor(private val cardListInteractor: CardListInteractor): Presenter<CardListView>() {

    fun loadAllCards() = GlobalScope.launch(Dispatchers.IO) {
        val cards = cardListInteractor.getAllCards()

        GlobalScope.launch(Dispatchers.Main) {
            view?.displayCards(cards)
        }.join()
    }
}