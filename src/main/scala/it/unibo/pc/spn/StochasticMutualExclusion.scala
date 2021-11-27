package it.unibo.pc.spn

import it.unibo.pc.multiset.MSet

import java.util.Random

object StochasticMutualExclusion extends App {
  // Specification of my data-type for states
  object place extends Enumeration {
    val N, T, C = Value
  }
  type Place = place.Value
  import SPN.*
  import place.*

  val spn = SPN[Place](
    (MSet(N), m=>1.0,MSet(T),MSet()),
    (MSet(T), m=>m(T),MSet(C),MSet(C)),
    (MSet(C), m=>2.0,MSet(),MSet())
  )
}