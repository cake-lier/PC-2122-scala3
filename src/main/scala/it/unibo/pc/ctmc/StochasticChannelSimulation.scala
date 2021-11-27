package it.unibo.pc.ctmc

import it.unibo.pc.utils.Time

import java.util.Random

object StochasticChannelSimulation extends App {
  
  import StochasticChannel.state.*

  val channel = StochasticChannel.stocChannel

  val channelAnalysis = CTMCSimulation(channel)
  Time.timed{
    println(channelAnalysis.newSimulationTrace(IDLE, new Random)
                           .take(10)
                           .toList
                           .mkString("\n"))
  }
}