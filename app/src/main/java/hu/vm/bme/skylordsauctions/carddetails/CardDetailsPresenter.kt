package hu.vm.bme.skylordsauctions.carddetails

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardDetailsPresenter @Inject constructor(private val interactor: CardDetailsInteractor) {
    private var view: CardDetailsView? = null

    fun attachView(view: CardDetailsView) {
        this.view = view
    }

    fun detachView() {
        view = null
    }
}