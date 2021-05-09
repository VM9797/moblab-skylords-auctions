package hu.vm.bme.skylordsauctions.service

import android.util.Log
import hu.vm.bme.skylordsauctions.data.AppDaoProvider
import hu.vm.bme.skylordsauctions.data.AppDatabase
import hu.vm.bme.skylordsauctions.network.cardbase.CardbaseApi
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.network.smj.SmjApi
import hu.vm.bme.skylordsauctions.network.smj.model.HistoryResponse
import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardService @Inject constructor(private val cardbaseApi: CardbaseApi,
                                      private val smjApi: SmjApi,
                                      private val daoProvider: AppDaoProvider) {

    private var cards: List<Card> = emptyList()

    companion object {
        private val finalStepSmjMappings = mapOf(
            Pair("amii-monument-b", "amii-monument"),
            Pair("curse-orb-b", "curse-orb"),
            Pair("curse-well-b", "curse-well"),
            Pair("jorne-b", "jorne"),
            Pair("lord-cyrian-promo", "lord-cyrian"),
            Pair("protector's-seal-(lost-souls)", "protector's-seal-ls"),
            Pair("protector's-seal-(twilight)", "protector's-seal-tl")
        )
    }

    suspend fun getDisplayableCards(): List<Card> {
        if (cards.isNotEmpty()) {
            return cards
        }

        val cardsWithImageName = cardbaseApi.getCardList().result?.filter { it.image?.name != null } ?: emptyList()

        cardsWithImageName.forEach {
            var smjId = it.image?.name ?: throw RuntimeException("This should not be possible")

            smjId = smjId.replace("[", "")
                .replace("]", "")
                .replace(" ", "-")
                .replace("(promo)", "promo")
                .toLowerCase()

            if (smjId.endsWith("-n")) {
                smjId = smjId.substring(0..smjId.length-3) + "-g"
            }

            if (smjId.endsWith("-f")) {
                smjId = smjId.substring(0..smjId.length-3) + "-r"
            }

            if (finalStepSmjMappings.containsKey(smjId)) {
                smjId = finalStepSmjMappings.getValue(smjId)
            }

            it.smjId = smjId
        }

        cards = cardsWithImageName
        return cards
    }

    suspend fun getNoteworthyPricesForCard(cardId: String): NoteworthyPrices {
        return smjApi.getNoteworthyPricesForCard(cardId).first()
    }

    suspend fun getAuctionHistoryForCard(cardId: String): HistoryResponse {
        return smjApi.getPriceHistoryForCard(cardId)
    }
}