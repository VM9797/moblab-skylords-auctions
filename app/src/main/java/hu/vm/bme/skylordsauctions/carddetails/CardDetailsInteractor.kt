package hu.vm.bme.skylordsauctions.carddetails

import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.service.CardService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardDetailsInteractor @Inject constructor(private val cardService: CardService) {

    suspend fun getCardByName(cardName: String): Card {
        return cardService.getDisplayableCards().first { it.smjId == cardName }
    }
}