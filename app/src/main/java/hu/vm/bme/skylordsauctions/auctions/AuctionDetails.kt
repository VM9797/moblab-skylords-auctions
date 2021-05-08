package hu.vm.bme.skylordsauctions.auctions

import com.github.mikephil.charting.data.Entry
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices

class AuctionDetails(
    val card: Card,
    val noteworthyPrices: NoteworthyPrices,
    val chartData: List<Entry>
) {

}