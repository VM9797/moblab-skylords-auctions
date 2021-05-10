package hu.vm.bme.skylordsauctions.addauction

import hu.vm.bme.skylordsauctions.mvp.Presenter
import hu.vm.bme.skylordsauctions.util.onIO
import hu.vm.bme.skylordsauctions.util.onMain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddAuctionPresenter @Inject constructor(private val interactor: AddAuctionInteractor): Presenter<AddAuctionView>() {

    fun createAuction(auctionCreate: AuctionCreate) = onIO {
        interactor.createAuction(auctionCreate)

        onMain {
            view?.auctionCreated()
        }.join()
    }
}