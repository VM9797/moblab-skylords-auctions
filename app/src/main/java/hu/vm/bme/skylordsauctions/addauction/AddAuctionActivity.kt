package hu.vm.bme.skylordsauctions.addauction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toolbar
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

        findViewById<Button>(R.id.btnCreate).setOnClickListener { onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun setActionBar(toolbar: Toolbar?) {
        super.setActionBar(toolbar)
        actionBar?.setTitle("Asd")
        supportActionBar.apply {
            this?.setHomeButtonEnabled(true)
            this?.setTitle("Asd")
        }
    }
}