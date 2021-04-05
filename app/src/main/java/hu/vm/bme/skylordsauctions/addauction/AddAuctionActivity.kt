package hu.vm.bme.skylordsauctions.addauction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.vm.bme.skylordsauctions.R
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
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}