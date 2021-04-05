package hu.vm.bme.skylordsauctions.carddetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.util.injector
import javax.inject.Inject

class CardDetailsActivity : AppCompatActivity(), CardDetailsView {

    @Inject
    lateinit var presenter: CardDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        injector.inject(this)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}