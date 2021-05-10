package hu.vm.bme.skylordsauctions.auctions

import com.github.mikephil.charting.data.Entry
import hu.vm.bme.skylordsauctions.service.CardService
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuctionsInteractor @Inject constructor(private val cardService: CardService) {

    suspend fun getAuctionDetails(cardName: String): AuctionDetails {
        val cardSmjIdPair = cardService.getCardSmjIdPairByCardName(cardName)
        val noteworthyPrices = cardService.getNoteworthyPricesForCard(cardSmjIdPair.second)
        val history = cardService.getAuctionHistoryForCard(cardSmjIdPair.second)

        val groupedData = history.priceData.groupBy {
            val startOfDay = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.time), TimeZone.getDefault().toZoneId())
                .toLocalDate()
                .atStartOfDay()
                .toEpochSecond(ZoneOffset.UTC)

            return@groupBy startOfDay
        }

        val chartData = groupedData.mapValues {
            return@mapValues it.value.minByOrNull { it.price }?.price ?: 0
        }.map {
            Entry(it.key.toFloat(), it.value.toFloat())
        }

        return AuctionDetails(cardSmjIdPair.first, noteworthyPrices, chartData)
    }
}