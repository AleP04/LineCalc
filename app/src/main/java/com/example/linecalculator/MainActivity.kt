package com.example.linecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.linecalculator.databinding.ActivityMainBinding
import com.jjoe64.graphview.*
import com.jjoe64.graphview.series.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculate.setOnClickListener { findEquation() }
        val graph = findViewById<View>(R.id.graph) as GraphView
        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);
        // activate horizontal scrolling
        graph.getViewport().setScrollable(true);
        // activate horizontal and vertical zooming and scrolling
        graph.getViewport().setScalableY(true);
        // activate vertical scrolling
        graph.getViewport().setScrollableY(true);}

    private fun findEquation() {
        val yA = binding.yA.text.toString().toIntOrNull()
        val yB = binding.yB.text.toString().toIntOrNull()
        val xA = binding.xA.text.toString().toIntOrNull()
        val xB = binding.xB.text.toString().toIntOrNull()
        var result: String
        if (yA == null || yB == null || xA == null || xB == null) {
            result = "coordinates not present, check and retry!"
            displayEquation(result)
            return
        }
        else{
        val m = (yB - yA)
        val q = yA - m * xA
        if (q >= 0)
            result = "y=$m" + "x+$q"
        else
            result = "y=$m" + "x$q"
        }
        displayEquation(result)
    }

    private fun displayEquation(result: String) {
        binding.result.text = getString(R.string.result_test, result)
        val graph = findViewById<View>(R.id.graph) as GraphView
        val lineSeries = LineGraphSeries(arrayOf<DataPoint>(
            DataPoint(1.0, 1.0),
            DataPoint(2.0, 2.0)
        ))
        graph.addSeries(lineSeries)
        val pointSeries = PointsGraphSeries(arrayOf<DataPoint>(
            DataPoint(1.0, 1.0),
            DataPoint(2.0,2.0))
        )
        graph.addSeries(pointSeries)

    }
}