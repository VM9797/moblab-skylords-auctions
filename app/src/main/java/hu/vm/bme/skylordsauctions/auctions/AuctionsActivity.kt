package hu.vm.bme.skylordsauctions.auctions

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import hu.vm.bme.skylordsauctions.R
import hu.vm.bme.skylordsauctions.util.CARD_DETAILS_CARD_NAME
import hu.vm.bme.skylordsauctions.util.injector
import java.lang.RuntimeException
import javax.inject.Inject

class AuctionsActivity : AppCompatActivity(), AuctionsView {
    @Inject
    lateinit var presenter: AuctionsPresenter

    private lateinit var chart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auctions)
        injector.inject(this)
        presenter.attachView(this)
        val cardName = intent.getStringExtra(CARD_DETAILS_CARD_NAME) ?: throw RuntimeException("Card name is required")
        setupChart()
        presenter.loadAuctionDetails(cardName)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayAuctionDetails(auctionDetails: AuctionDetails) {
        setTextAndDisplay(findViewById(R.id.tvLastMinimumPrice), "${auctionDetails.noteworthyPrices.currentPrice.price}")
        setTextAndDisplay(findViewById(R.id.tvMeanMinimumPrice), "${auctionDetails.noteworthyPrices.meanPrice.price}")
        setTextAndDisplay(findViewById(R.id.tvLowestMinimumPrice), "${auctionDetails.noteworthyPrices.minPrice.price}")
        setTextAndDisplay(findViewById(R.id.tvHighestMinimumPrice), "${auctionDetails.noteworthyPrices.maxPrice.price}")

        Glide.with(this)
            .load(auctionDetails.card.imageUrl)
            .into(findViewById(R.id.ivCard))

        findViewById<ProgressBar>(R.id.progressBar_cyclic).visibility = View.GONE
        setChartData(auctionDetails.chartData)
    }

    private fun setTextAndDisplay(textView: TextView, text: String) {
        textView.text = text
        textView.visibility = View.VISIBLE
    }

    private fun setupChart() {
        chart = findViewById(R.id.chart)
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)

        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        chart.xAxis.setDrawLabels(false)
    }

    private fun setChartData(chartData: List<Entry>) {
        val dataSet = LineDataSet(chartData, "Price history").apply {
            setDrawIcons(false)
            setColor(Color.BLACK)
            setCircleColor(Color.BLACK)

            lineWidth = 1f
            circleRadius = 3f

            setDrawCircleHole(false)

            valueTextSize = 9f

        }

        val dataSets = listOf(dataSet)

        chart.data = LineData(dataSets)
        chart.animateX(1500)
    }
}