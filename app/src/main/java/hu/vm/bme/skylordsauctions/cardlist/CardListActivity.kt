package hu.vm.bme.skylordsauctions.cardlist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.network.smj.SmjApi
import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices
import hu.vm.bme.skylordsauctions.util.injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardListActivity: AppCompatActivity(), CardListView {

    @Inject
    lateinit var cardListPresenter: CardListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injector.inject(this)
        cardListPresenter.attachView(this)
        cardListPresenter.loadAllCards()
    }

    override fun onDestroy() {
        super.onDestroy()
        cardListPresenter.detachView()
    }

    override fun logPriceInfo(noteworthyPrices: NoteworthyPrices) {
        Log.i("Skylords", noteworthyPrices.toString())
    }

    override fun displayCardInfo(card: Card) {
        Log.i("Skylords", card.image?.name ?: "No name")
        cardListPresenter.getNoteworthyPricesForCard(card)
    }
}