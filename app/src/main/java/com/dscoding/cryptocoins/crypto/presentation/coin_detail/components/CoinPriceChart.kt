package com.dscoding.cryptocoins.crypto.presentation.coin_detail.components

import android.content.Context
import android.graphics.Typeface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dscoding.cryptocoins.crypto.domain.CoinPrice
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CoinPriceChart(prices: List<CoinPrice>, modifier: Modifier = Modifier) {
    val entries = prices.map {
        Entry(it.dateTime.toEpochSecond().toFloat(), it.priceUsd.toFloat())
    }

    val style = LineChartStyle(
        lineColor = MaterialTheme.colorScheme.primary.toArgb(),
        valueColor = MaterialTheme.colorScheme.onSurface.toArgb(),
        gridColor = MaterialTheme.colorScheme.onSurface.toArgb()
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AndroidView(
            factory = { context -> createLineChart(context, entries, style) },
            modifier = Modifier.fillMaxSize() // Ensures the chart takes up the available space
        )
    }
}

data class LineChartStyle(
    val lineColor : Int,
    val valueColor : Int,
    val gridColor : Int
)

fun createLineChart(context: Context, entries: List<Entry>, style: LineChartStyle): LineChart {
    val lineChart = LineChart(context)

    val dataSet = LineDataSet(entries, "").apply {
        color = style.lineColor
        valueTextColor = style.valueColor
        lineWidth = 2f
        setDrawValues(false)
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER
    }

    val lineData = LineData(dataSet)
    lineChart.data = lineData

    // Configure Price Axis Left
    lineChart.axisLeft.apply {
        isEnabled = true
        setDrawLabels(true)
        textColor = style.valueColor
        valueFormatter = object : ValueFormatter() {
            private val formatter = DecimalFormat("$ #,###") // Format for Y-axis labels

            override fun getFormattedValue(value: Float): String {
                return formatter.format(value)
            }
        }
        setDrawGridLines(true)
        gridColor = style.gridColor
        gridLineWidth = 1f

        setLabelCount(3, true)
    }

    // Configure Date X-Axis
    lineChart.xAxis.apply {
        position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM // Set X-axis to the bottom
        valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())

            override fun getFormattedValue(value: Float): String {
                val millis = value.toLong() * 1000
                return dateFormat.format(millis)
            }
        }
        setDrawGridLines(true)
        gridColor = style.gridColor
        gridLineWidth = 1f
        setLabelCount(6, true)
        textColor = style.valueColor

        // Adjust label rotation to prevent overlap
        granularity = 1f // Allow spacing between labels
    }

    lineChart.axisRight.isEnabled = false

    // Remove the description
    lineChart.description.isEnabled = false

    // Apply Material Theme font to text (if available in your theme)
    lineChart.xAxis.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL) // Replace with your specific font if needed
    lineChart.axisLeft.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL) // Replace with your specific font if needed

    lineChart.invalidate()

    return lineChart
}