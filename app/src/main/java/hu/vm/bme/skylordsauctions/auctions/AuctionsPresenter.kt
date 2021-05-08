package hu.vm.bme.skylordsauctions.auctions

import hu.vm.bme.skylordsauctions.mvp.Presenter
import hu.vm.bme.skylordsauctions.util.onIO
import hu.vm.bme.skylordsauctions.util.onMain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuctionsPresenter @Inject constructor(private val interactor: AuctionsInteractor): Presenter<AuctionsView>() {

    fun loadAuctionDetails(cardName: String) = onIO {
        val auctionDetails = interactor.getAuctionDetails(cardName)

        onMain { view?.displayAuctionDetails(auctionDetails) }
    }
}