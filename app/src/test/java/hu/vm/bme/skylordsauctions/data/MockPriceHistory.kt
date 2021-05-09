package hu.vm.bme.skylordsauctions.data

import hu.vm.bme.skylordsauctions.network.smj.model.HistoryResponse
import hu.vm.bme.skylordsauctions.network.smj.model.PriceData

object MockPriceHistory {
    val abominationPriceHistory = HistoryResponse(
        listOf(
            PriceData(1577883661000, 20),
            PriceData(1577883661001, 30),
            PriceData(1580562061000, 60)
        ),
        "abomination-b"
    )
}