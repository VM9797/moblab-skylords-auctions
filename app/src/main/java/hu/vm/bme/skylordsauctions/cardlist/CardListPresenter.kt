package hu.vm.bme.skylordsauctions.cardlist

import android.util.Log
import hu.vm.bme.skylordsauctions.mvp.Presenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardListPresenter @Inject constructor(val cardListInteractor: CardListInteractor): Presenter<CardListView>() {
}