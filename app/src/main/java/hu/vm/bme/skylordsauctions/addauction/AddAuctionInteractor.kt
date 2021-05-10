package hu.vm.bme.skylordsauctions.addauction

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddAuctionInteractor @Inject constructor() {

    suspend fun createAuction(auctionCreate: AuctionCreate) {
        // Mock the network delay
        delay(500L)
    }
}