package hu.vm.bme.skylordsauctions.cardlist

import android.util.Log
import hu.vm.bme.skylordsauctions.data.AppDatabase
import hu.vm.bme.skylordsauctions.network.cardbase.CardbaseApi
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.network.cardbase.model.CardbaseResult
import hu.vm.bme.skylordsauctions.network.smj.SmjApi
import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices
import hu.vm.bme.skylordsauctions.service.CardService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardListInteractor @Inject constructor(private val cardService: CardService) {
    suspend fun getNoteworthyPricesForCard(card: Card): NoteworthyPrices {
        return cardService.getNoteworthyPricesForCard(card.smjId)
    }

    suspend fun getAllCards(): List<Card> {
        return cardService.getDisplayableCards()
    }
}