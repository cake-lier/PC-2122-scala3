package it.unibo.pc

trait CTMC[S] {
  // rate + state
  def transitions(a: S): Set[(Double, S)]
}

object CTMC {

  def ofFunction[S](f: PartialFunction[S, Set[(Double, S)]]): CTMC[S] =
    (s: S) => f.applyOrElse(s, (_: S) => Set[(Double, S)]())

  def ofRelation[S](rel: Set[(S, Double, S)]): CTMC[S] = ofFunction { case s =>
    rel filter { _._1 == s } map { t => (t._2, t._3) }
  }

  def ofTransitions[S](rel: (S, Double, S)*): CTMC[S] = ofRelation(rel.toSet)
}
