package hu.vm.bme.skylordsauctions.auctions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.util.injector
import javax.inject.Inject

class AuctionsActivity : AppCompatActivity(), AuctionsView {
    @Inject
    lateinit var presenter: AuctionsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auctions)
        injector.inject(this)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}