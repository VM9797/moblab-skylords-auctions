package hu.vm.bme.skylordsauctions.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class MvpActivity: AppCompatActivity(), MvpView {
    abstract val presenter: Presenter<MvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wireDependencies()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    abstract fun wireDependencies()


}