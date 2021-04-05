package hu.vm.bme.skylordsauctions.carddetails

import hu.vm.bme.skylordsauctions.mvp.Presenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardDetailsPresenter @Inject constructor(private val interactor: CardDetailsInteractor): Presenter<CardDetailsView>()