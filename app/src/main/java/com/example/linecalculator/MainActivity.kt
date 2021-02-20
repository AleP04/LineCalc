package com.example.linecalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.blue
import com.example.linecalculator.databinding.ActivityMainBinding
import com.jjoe64.graphview.*
import com.jjoe64.graphview.series.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculate.setOnClickListener { calculateAndDraw() }
    }

    private fun calculateAndDraw() {//pulisci grafico per prima cosa
        val graph = findViewById<View>(R.id.graph) as GraphView
        val yA = binding.yA.text.toString().toDoubleOrNull()
        val yB = binding.yB.text.toString().toDoubleOrNull()
        val xA = binding.xA.text.toString().toDoubleOrNull()
        val xB = binding.xB.text.toString().toDoubleOrNull()
        var extension: Double = 4.0
        val extxA: Double
        val extyA: Double
        val extxB: Double
        val extyB: Double
        val m: Double
        val q: Double
        val result: String
        if (yA == null || yB == null || xA == null || xB == null) {
            result = "coordinates not present, check and retry!"
            displayEquation(result)
            return
        } else {
            m = (yB - yA)/(xB-xA)
            q = yA - (m * xA)
            if (q >= 0)
                result = "y=$m" + "x+$q"
            else
                result = "y=$m" + "x$q"
        }
        displayEquation(result)
        //generate extensions
        if (xA > xB) {
            extxA = xA + extension
            extyA = (extxA * m) + q
            extxB = xB - extension
            extyB = (extxB * m) + q
        } else {
            extxA = xA - extension
            extyA = (extxA * m) + q
            extxB = xB + extension
            extyB = (extxB * m) + q
        }
        drawLine(graph, extxA, extyA, extxB, extyB)
        drawPoints(graph, xA, yA, xB, yB)
    }

    private fun displayEquation(result: String) {
        binding.result.text = getString(R.string.result_test, result)
    }

    fun drawPoints(
        graph: GraphView,
        xA: Double,
        yA: Double,
        xB: Double,
        yB: Double
    ) {
        var series = PointsGraphSeries(
            arrayOf<DataPoint>(
            )
        )
        if (xA < xB) {
            series = PointsGraphSeries(//controlla quali sono i maggiori ed i minori
                arrayOf<DataPoint>(
                    DataPoint(xA, yA),
                    DataPoint(xB, yB)
                )
            )
        } else {
            series = PointsGraphSeries(//controlla quali sono i maggiori ed i minori
                arrayOf<DataPoint>(
                    DataPoint(xB, yB),
                    DataPoint(xA, yA)
                )
            )
        }

        graph.addSeries(series)
        series.setColor(Color.RED)
    }

    fun drawLine(graph: GraphView, extxA: Double, extyA: Double, extxB: Double, extyB: Double) {//inserisci le estensioni dei punti come parametri
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.isScalable = true
        graph.viewport.setScalableY(true)
        var extendLine = LineGraphSeries(
            arrayOf<DataPoint>(
            )
        )
        if (extxA < extxB) {
            extendLine = LineGraphSeries(//controlla quali sono i maggiori ed i minori
                arrayOf<DataPoint>(
                    DataPoint(extxA, extyA),
                    DataPoint(extxB, extyB)
                )
            )
            graph.viewport.setMinX(extxA)//stesso valore di extend line
            graph.viewport.setMaxX(extxB)//stesso valore di extend line
        } else {
            extendLine = LineGraphSeries(//controlla quali sono i maggiori ed i minori
                arrayOf<DataPoint>(
                    DataPoint(extxB, extyB),
                    DataPoint(extxA, extyA)
                )
            )
            graph.viewport.setMinX(extxB)//stesso valore di extend line
            graph.viewport.setMaxX(extxA)//stesso valore di extend line
        }
        if(extyA<extyB) {
            graph.viewport.setMinY(extyA)
            graph.viewport.setMaxY(extyB)
        }else{
            graph.viewport.setMinY(extyB)
            graph.viewport.setMaxY(extyB)
        }
        graph.addSeries(extendLine)
    }

}