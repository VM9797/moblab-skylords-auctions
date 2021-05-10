package hu.vm.bme.skylordsauctions.service

import hu.vm.bme.skylordsauctions.data.AppDaoProvider
import hu.vm.bme.skylordsauctions.data.CardNameIdMapping
import hu.vm.bme.skylordsauctions.network.cardbase.CardbaseApi
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.network.smj.SmjApi
import hu.vm.bme.skylordsauctions.network.smj.model.HistoryResponse
import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices
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

        val mappings = cardsWithImageName.map {
            var smjId = it.image?.name ?: throw RuntimeException("This should not be possible")

            smjId = smjId.replace("[", "")
                .replace("]", "")
                .replace(" ", "-")
                .replace("(promo)", "promo")
                .toLowerCase()

            if (smjId.endsWith("-n")) {
                smjId = smjId.substring(0..smjId.length-3) + "-g"
            }

            if (finalStepSmjMappings.containsKey(smjId)) {
                smjId = finalStepSmjMappings.getValue(smjId)
            }

            CardNameIdMapping(it.imageName, smjId)
        }

        val dao = daoProvider.cardNameIdMappingDao()
        val dbMappings = dao.getAllMappings()

        val missingDbMappings = mappings.filter { mapping ->
            dbMappings.find { it.cardName == mapping.cardName } == null
        }

        dao.insertMappings(*missingDbMappings.toTypedArray())

        cards = cardsWithImageName
        return cards
    }

    suspend fun getCardSmjIdPairByCardName(cardName: String): Pair<Card, String> {
        val cardNameMapping = daoProvider.cardNameIdMappingDao().getMappingByCardName(cardName)
            ?: throw RuntimeException("Card name mapping does not exist for card name $cardName")

        val card = getDisplayableCards().find { it.imageName == cardName }
            ?: throw RuntimeException("Could not find card with name $cardName")

        return Pair(card, cardNameMapping.smjCardId)
    }

    suspend fun getNoteworthyPricesForCard(cardId: String): NoteworthyPrices {
        return smjApi.getNoteworthyPricesForCard(cardId).first()
    }

    suspend fun getAuctionHistoryForCard(cardId: String): HistoryResponse {
        return smjApi.getPriceHistoryForCard(cardId)
    }
}