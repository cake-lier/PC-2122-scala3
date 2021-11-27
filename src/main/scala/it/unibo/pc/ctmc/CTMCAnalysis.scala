package pc.modelling

import it.unibo.pc.ctmc.{CTMC, CTMCSimulation}

import java.util.Random

trait CTMCAnalysis[S] extends CTMCSimulation[S] { self: CTMC[S] =>

  type Property[A] = Trace[A] => Boolean

  private def createMarkingCondition[A](condition: A => Boolean): ((Double, A)) => Boolean = e => condition(e._2)

  def eventually[A](filt: A => Boolean): Property[A] = _.exists(createMarkingCondition(filt))

  // takes a property and makes it time bounded by the magic of streams
  def bounded[A](timeBound: Double)(prop: Property[A]): Property[A] = t => prop(t.takeWhile(e => e._1 <= timeBound))

  // a PRISM-like experiment, giving a statistical result (in [0,1])
  def experiment(runs: Int = 10000, prop: Property[S], rnd: Random = new Random, s0: S, timeBound: Double): Double =
    (0 until runs).count(_ => bounded(timeBound)(prop)(newSimulationTrace(s0, rnd))).toDouble / runs
}

object CTMCAnalysis {
  def apply[S](ctmc: CTMC[S]): CTMCAnalysis[S] = new CTMC[S] with CTMCAnalysis[S] {
    override def transitions(s: S): Set[(Double, S)] = ctmc.transitions(s)
  }
}