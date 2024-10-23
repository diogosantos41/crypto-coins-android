package com.dscoding.cryptocoins.crypto.presentation.coin_detail.components

import android.graphics.Typeface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dscoding.cryptocoins.crypto.domain.CoinPrice
import com.dscoding.cryptocoins.crypto.presentation.coin_detail.LineChartStyle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CoinPriceChart(prices: List<CoinPrice>, style: LineChartStyle, modifier: Modifier = Modifier) {

    val entries = prices.map {
        Entry(it.dateTime.toEpochSecond().toFloat(), it.priceUsd.toFloat())
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AndroidView(
            factory = { context -> createLineChart(LineChart(context), entries, style) },
            modifier = Modifier.fillMaxSize()
        )
    }
}



fun createLineChart(lineChart: LineChart, entries: List<Entry>, style: LineChartStyle): LineChart {

    val dataSet = LineDataSet(entries, "").apply {
        color = style.lineColor
        valueTextColor = style.valueColor
        lineWidth = style.lineWidth
        setDrawValues(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER
    }

    val lineData = LineData(dataSet)
    lineChart.data = lineData

    lineChart.axisLeft.apply {
        valueFormatter = object : ValueFormatter() {
            private val formatter = DecimalFormat("$ #,###")

            override fun getFormattedValue(value: Float): String {
                return formatter.format(value)
            }
        }
        gridColor = style.gridColor
        gridLineWidth = style.gridLineWidth
        textColor = style.valueColor

        setLabelCount(3, true)
    }

    lineChart.xAxis.apply {
        position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())

            override fun getFormattedValue(value: Float): String {
                val millis = value.toLong() * 1000
                return dateFormat.format(millis)
            }
        }
        gridColor = style.gridColor
        gridLineWidth = style.gridLineWidth
        setLabelCount(6, true)
        textColor = style.valueColor
    }

    lineChart.axisRight.isEnabled = false
    lineChart.description.isEnabled = false
    lineChart.legend.isEnabled = false

    lineChart.setTouchEnabled(false)

    lineChart.xAxis.typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
    lineChart.axisLeft.typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)

    lineChart.invalidate()

    return lineChart
}