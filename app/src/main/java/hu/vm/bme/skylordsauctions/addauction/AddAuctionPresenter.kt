package hu.vm.bme.skylordsauctions.addauction

import hu.vm.bme.skylordsauctions.mvp.Presenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddAuctionPresenter @Inject constructor(private val interactor: AddAuctionInteractor): Presenter<AddAuctionView>() {
}