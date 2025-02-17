package it.unibo.pc.multiset

// Some simple client code.. also check MSetSpec and MSetCheck
object TryMSet extends App {

  val m1 = MSet(10, 20, 30, 30, 40, 40, 50)
  val m2: MSet[Int] = MSet(50, 10, 20, 30, 30, 40, 40)
  println(m1)
  println(m1 == m2)
  println(m1.asList)
  println(m1.asMap)
  println(m1.iterator.toList)
  println(m1.matchIterator.toList)
  println(m1.extract(MSet(50, 10)))
}
