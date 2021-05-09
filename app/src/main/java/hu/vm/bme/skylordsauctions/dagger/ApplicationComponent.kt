package hu.vm.bme.skylordsauctions.dagger

import dagger.Component
import hu.vm.bme.skylordsauctions.addauction.AddAuctionActivity
import hu.vm.bme.skylordsauctions.auctions.AuctionsActivity
import hu.vm.bme.skylordsauctions.carddetails.CardDetailsActivity
import hu.vm.bme.skylordsauctions.cardlist.CardListActivity
import hu.vm.bme.skylordsauctions.data.DataModule
import hu.vm.bme.skylordsauctions.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModule::class])
interface ApplicationComponent {
    fun inject(cardListActivity: CardListActivity)
    fun inject(cardDetailsActivity: CardDetailsActivity)
    fun inject(auctionsActivity: AuctionsActivity)
    fun inject(addAuctionActivity: AddAuctionActivity)
}