package hu.vm.bme.skylordsauctions.mvp

abstract class Presenter<TView> {
    private var view: TView? = null

    fun attachView(view: TView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}