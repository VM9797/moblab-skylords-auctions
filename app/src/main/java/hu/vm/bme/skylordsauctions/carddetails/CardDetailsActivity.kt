package hu.vm.bme.skylordsauctions.carddetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.addauction.AddAuctionActivity
import hu.vm.bme.skylordsauctions.auctions.AuctionsActivity
import hu.vm.bme.skylordsauctions.common.*
import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.util.CARD_DETAILS_CARD_NAME
import hu.vm.bme.skylordsauctions.util.injector
import java.lang.RuntimeException
import javax.inject.Inject

class CardDetailsActivity : AppCompatActivity(), CardDetailsView {

    @Inject
    lateinit var presenter: CardDetailsPresenter

    private lateinit var cardName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_card_details)
        injector.inject(this)
        presenter.attachView(this)

        cardName = intent.getStringExtra(CARD_DETAILS_CARD_NAME) ?: throw RuntimeException("Card name is mandatory for card details")
        presenter.getCard(cardName)

        findViewById<FloatingActionButton>(R.id.fabAddAuction).setOnClickListener { launchAddAuctionActivity() }
        findViewById<Button>(R.id.btnPriceInfo).setOnClickListener { launchAuctionsActivity() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayCardDetails(card: Card) {
        val iv = findViewById<ImageView>(R.id.ivCardDetail)

        Glide.with(this)
            .load(card.imageUrl)
            .into(iv)

        findViewById<TextView>(R.id.tvRarity).text = "${Rarity.parseInt(card.rarity)}"
        findViewById<TextView>(R.id.tvCategory).text = "${card.category}"
        findViewById<TextView>(R.id.tvTier).text = "${card.orbInfo?.orbCode}"
        findViewById<TextView>(R.id.tvAffinity).text = "${Affinity.parseInt(card.affinity)}"
        findViewById<TextView>(R.id.tvAttack).text = "${card.offense}"
        findViewById<TextView>(R.id.tvDefense).text = "${card.defense}"
        findViewById<TextView>(R.id.tvAttackType).text = "${OffenseType.parseInt(card.offenseType)}"
        findViewById<TextView>(R.id.tvDefenseType).text = "${DefenseType.parseInt(card.defenseType)}"
        findViewById<TextView>(R.id.tvEdition).text = "${Edition.parseInt(card.edition)}"
    }

    private fun <T> launchActivity(cls: Class<T>) {
        val intent = Intent(this, cls).apply {
            putExtra(CARD_DETAILS_CARD_NAME, cardName)
        }
        startActivity(intent)
    }

    private fun launchAuctionsActivity() {
        launchActivity(AuctionsActivity::class.java)
    }

    private fun launchAddAuctionActivity() {
        launchActivity(AddAuctionActivity::class.java)
    }
}