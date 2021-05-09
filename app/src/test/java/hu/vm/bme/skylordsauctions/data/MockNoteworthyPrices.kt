package hu.vm.bme.skylordsauctions.data

import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices
import hu.vm.bme.skylordsauctions.network.smj.model.PriceData

object MockNoteworthyPrices {
    val abominationPrices: NoteworthyPrices = NoteworthyPrices(
        "abomination-b",
        PriceData(10, 20),
        PriceData(30, 40),
        PriceData(50, 60),
        PriceData(70, 80)
    )
}