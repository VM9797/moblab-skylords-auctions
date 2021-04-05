package hu.vm.bme.skylordsauctions.cardlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.mvp.MvpActivity
import hu.vm.bme.skylordsauctions.mvp.MvpView
import hu.vm.bme.skylordsauctions.mvp.Presenter
import hu.vm.bme.skylordsauctions.util.injector
import javax.inject.Inject

class CardListActivity: AppCompatActivity(), CardListView {

    @Inject
    lateinit var cardListPresenter: CardListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injector.inject(this)
        cardListPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        cardListPresenter.detachView()
    }
}