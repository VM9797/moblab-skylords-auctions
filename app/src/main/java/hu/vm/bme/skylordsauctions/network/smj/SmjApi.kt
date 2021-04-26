package hu.vm.bme.skylordsauctions.network.smj

import hu.vm.bme.skylordsauctions.network.smj.model.HistoryResponse
import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices
import retrofit2.http.GET
import retrofit2.http.Path

interface SmjApi {

    @GET("prices/{cardId}")
    suspend fun getPriceHistoryForCard(@Path("cardId") cardId: String): HistoryResponse

    @GET("prices/noteworthy/{cardId}")
    suspend fun getNoteworthyPricesForCard(@Path("cardId") cardId: String): List<NoteworthyPrices>

}