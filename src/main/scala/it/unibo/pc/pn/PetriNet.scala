package it.unibo.pc.pn

import it.unibo.pc.fa.System
import it.unibo.pc.multiset.MSet

object PetriNet {

  /**
   * A Petri-net is composed by a triple: `pre-condition`, `effects`, `inhibition`.
   */
  type PetriNet[P] = Set[(MSet[P], MSet[P], MSet[P])]

  /**
   * Create a new Petri-net from a list of triple representing the model.
   *
   * @param transitions
   * list of tranitions.
   * @return
   * the set of the transitions of the model.
   */
  def apply[P](transitions: (MSet[P], MSet[P], MSet[P])*): PetriNet[P] = transitions.toSet

  def toPartialFunction[P](pn: PetriNet[P]): PartialFunction[MSet[P], Set[MSet[P]]] = {
    case m =>
      for {
        (cond, eff, inh) <- pn if (m disjoined inh)
        out <- m extract cond
      } yield out union eff
  }

  // factory of A System
  def toSystem[P](pn: PetriNet[P]): System[MSet[P]] = System.ofFunction(toPartialFunction(pn))

  // Syntactic sugar to write transitions as:  MSet(a,b,c) ~~> MSet(d,e)
  extension[P](self: MSet[P]) {
    def ~~> (y: MSet[P]): (MSet[P], MSet[P], MSet[P]) = (self, y, MSet[P]())
  }

  // Syntactic sugar to write transitions as:  MSet(a,b,c) ~~> MSet(d,e) ^^^ MSet(f)
  extension[P](self: (MSet[P], MSet[P], MSet[P])) {
    def ^^^ (z: MSet[P]): (MSet[P], MSet[P], MSet[P]) = (self._1, self._2, z)
  }
}
