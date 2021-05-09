package hu.vm.bme.skylordsauctions.cardlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.carddetails.CardDetailsActivity
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.network.smj.SmjApi
import hu.vm.bme.skylordsauctions.network.smj.model.NoteworthyPrices
import hu.vm.bme.skylordsauctions.util.CARD_DETAILS_CARD_NAME
import hu.vm.bme.skylordsauctions.util.injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardListActivity: AppCompatActivity(), CardListView {

    @Inject
    lateinit var cardListPresenter: CardListPresenter

    private lateinit var rvAdapter: CardViewRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injector.inject(this)
        cardListPresenter.attachView(this)

        rvAdapter = CardViewRvAdapter(this) {
            launchCardDetailsActivity(it)
        }

        val rv = findViewById<RecyclerView>(R.id.rvCards)
        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(this, 2)

        cardListPresenter.loadAllCards()
    }

    override fun onDestroy() {
        super.onDestroy()
        cardListPresenter.detachView()
    }

    override fun displayCards(cards: List<Card>) {
        rvAdapter.items = cards
        rvAdapter.notifyDataSetChanged()
    }

    private fun launchCardDetailsActivity(card: Card) {
        Intent(this, CardDetailsActivity::class.java).apply {
            putExtra(CARD_DETAILS_CARD_NAME, card.smjId)
            startActivity(this)
        }
    }

}