package it.unibo.pc.ctmc

import it.unibo.pc.utils.Time
import pc.modelling.CTMCAnalysis

import math.BigDecimal.double2bigDecimal
import scala.collection.parallel.CollectionConverters.*
import de.sciss.chart.module.XYDatasetConversions.ToXYDataset.FromTuple2s
import de.sciss.chart.api.XYLineChart
import org.jfree.chart.StandardChartTheme
import org.jfree.data.xy.DefaultXYDataset

import scala.collection.parallel.ParSeq


object StochasticChannelExperiment extends App {

  import StochasticChannel.state.*

  val channel = StochasticChannel.stocChannel

  val channelAnalysis = CTMCAnalysis(channel)
  val data = for {
    t <- (0.1 to 10.0 by 0.1).par; //parallel execution
    v = t.toDouble
    p = channelAnalysis.experiment(
      runs = 19000,
      prop = channelAnalysis.eventually(_ == DONE),
      s0 = IDLE,
      timeBound = v)
  } yield (v, p)

  Time.timed{ println(data.mkString("\n")) }
  val series = data.seq.unzip[Double, Double]
  val dataset = DefaultXYDataset()
  dataset.addSeries(0, Array(Array(series._1: _*), Array(series._2: _*)))
  XYLineChart(dataset).show()
}