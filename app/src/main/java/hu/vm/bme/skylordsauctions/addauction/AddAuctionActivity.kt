package hu.vm.bme.skylordsauctions.addauction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.common.AuctionLength
import hu.vm.bme.skylordsauctions.util.injector
import javax.inject.Inject

class AddAuctionActivity : AppCompatActivity(), AddAuctionView {
    @Inject
    lateinit var presenter: AddAuctionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_auction)
        injector.inject(this)
        presenter.attachView(this)

        findViewById<Button>(R.id.btnCreate).setOnClickListener { submitAuction() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun auctionCreated() {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        onBackPressed()
    }

    private fun submitAuction() {
        presenter.createAuction(
            AuctionCreate(
                findViewById<EditText>(R.id.etStartingBid).text.toString().toInt(),
                findViewById<EditText>(R.id.etBuyoutPrice).text.toString().toInt(),
                AuctionLength.parseFromStringRepresentation(findViewById<Spinner>(R.id.spAuctionLength).selectedItem.toString())
            )
        )
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
    }
}