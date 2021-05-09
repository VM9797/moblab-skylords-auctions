package hu.vm.bme.skylordsauctions.carddetails

import hu.vm.bme.skylordsauctions.mvp.Presenter
import hu.vm.bme.skylordsauctions.util.onIO
import hu.vm.bme.skylordsauctions.util.onMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardDetailsPresenter @Inject constructor(private val interactor: CardDetailsInteractor): Presenter<CardDetailsView>() {

    fun getCard(cardName: String) = onIO {
        val card = interactor.getCardByName(cardName)

        onMain { view?.displayCardDetails(card) }.join()
    }
}