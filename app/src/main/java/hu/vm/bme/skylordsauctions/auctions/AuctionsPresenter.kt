package hu.vm.bme.skylordsauctions.auctions

import hu.vm.bme.skylordsauctions.mvp.Presenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuctionsPresenter @Inject constructor(private val interactor: AuctionsInteractor): Presenter<AuctionsView>() {
}