package hu.vm.bme.skylordsauctions.network.cardbase

import hu.vm.bme.skylordsauctions.network.cardbase.model.CardbaseResult
import retrofit2.http.GET

interface CardbaseApi {
    @GET("GetCards")
    suspend fun getCardList(): CardbaseResult
}