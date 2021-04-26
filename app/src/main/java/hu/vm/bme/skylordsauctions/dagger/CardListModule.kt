package hu.vm.bme.skylordsauctions.dagger

import dagger.Module
import dagger.Provides
import hu.vm.bme.skylordsauctions.cardlist.CardListInteractor
import hu.vm.bme.skylordsauctions.cardlist.CardListPresenter
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class CardListModule {
    @Provides
    @Singleton
    fun provideCardListPresenter(cardListInteractor: CardListInteractor) = CardListPresenter(cardListInteractor)
}