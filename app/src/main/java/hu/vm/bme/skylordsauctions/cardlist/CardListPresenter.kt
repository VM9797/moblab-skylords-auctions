package hu.vm.bme.skylordsauctions.cardlist

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardListPresenter @Inject constructor(val cardListInteractor: CardListInteractor) {
    private var view: CardListView? = null

    fun attachView(view: CardListView) {
        this.view = view
    }

    fun detachView() {
        view = null
    }
}