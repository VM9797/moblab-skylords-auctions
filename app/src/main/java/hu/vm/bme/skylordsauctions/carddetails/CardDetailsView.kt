package hu.vm.bme.skylordsauctions.carddetails

import hu.vm.bme.skylordsauctions.network.cardbase.model.Card

interface CardDetailsView {
    fun displayCardDetails(card: Card)
}