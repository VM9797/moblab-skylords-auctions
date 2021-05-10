package hu.vm.bme.skylordsauctions.mvp

import hu.vm.bme.skylordsauctions.addauction.AddAuctionInteractor
import hu.vm.bme.skylordsauctions.addauction.AddAuctionPresenter
import hu.vm.bme.skylordsauctions.addauction.AddAuctionView
import hu.vm.bme.skylordsauctions.addauction.AuctionCreate
import hu.vm.bme.skylordsauctions.common.AuctionLength
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class AddAuctionMvpTest {

    @Test
    fun presenterShouldInvokeView() {
        Dispatchers.setMain(newSingleThreadContext("UI thread"))

        val interactor = Mockito.mock(AddAuctionInteractor::class.java)
        val view = Mockito.mock(AddAuctionView::class.java)

        val presenter = AddAuctionPresenter(interactor)
        presenter.attachView(view)

        val auctionCreate = AuctionCreate(10, 20, AuctionLength.Long)

        runBlocking {
            val job = presenter.createAuction(auctionCreate)
            job.join()

            Assert.assertEquals(1, Mockito.mockingDetails(view).invocations.size)
        }

        Dispatchers.resetMain()

    }

}