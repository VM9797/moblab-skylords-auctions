package hu.vm.bme.skylordsauctions.cardlist

import hu.vm.bme.skylordsauctions.mvp.MvpView
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices

interface CardListView: MvpView {
    fun displayCards(cards: List<Card>)
}