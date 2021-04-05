package hu.vm.bme.skylordsauctions.cardlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.util.injector
import javax.inject.Inject

class CardListActivity: AppCompatActivity(), CardListView {
    @Inject
    lateinit var cardListPresenter: CardListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injector.inject(this)
    }
}