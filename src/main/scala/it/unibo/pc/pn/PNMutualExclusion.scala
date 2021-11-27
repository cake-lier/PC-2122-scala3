package it.unibo.pc.pn

import it.unibo.pc.multiset.MSet
import it.unibo.pc.pn.PetriNet.*

object PNMutualExclusion extends App {

  object place extends Enumeration {
    val N, T, C = Value
  }
  type Place = place.Value
  import place.*

  // DSL-like specification of A Petri Net
  def mutualExclusionSystem() = toSystem(PetriNet[Place](
    MSet(N) ~~> MSet(T),
    MSet(T) ~~> MSet(C) ^^^ MSet(C),
    MSet(C) ~~> MSet())
  )

  // example usage
  println(mutualExclusionSystem().paths(MSet(N,N),7).toList.mkString("\n"))
}
