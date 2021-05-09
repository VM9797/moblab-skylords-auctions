package hu.vm.bme.skylordsauctions.data

import hu.vm.bme.skylordsauctions.network.cardbase.model.CardbaseResult

object MockCardbaseResult {
    val result: CardbaseResult = CardbaseResult(
        exception = null,
        success = true,
        result = arrayOf(
            MockCards.abomination,
            MockCards.amiiMonument,
            MockCards.protectorsSeal,
            MockCards.shaman,
            MockCards.banditos,
            MockCards.cardWithNoImage
        )
    )
}