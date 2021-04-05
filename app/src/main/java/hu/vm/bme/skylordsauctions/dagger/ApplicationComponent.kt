package hu.vm.bme.skylordsauctions.dagger

import dagger.Component
import hu.vm.bme.skylordsauctions.cardlist.CardListActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [CardListModule::class])
interface ApplicationComponent {
    fun inject(cardListActivity: CardListActivity)
}