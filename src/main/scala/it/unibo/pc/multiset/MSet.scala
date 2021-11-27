package it.unibo.pc.multiset

// A multiset datatype
trait MSet[A] extends (A => Int) {

  def union(m: MSet[A]): MSet[A]

  def diff(m: MSet[A]): MSet[A]

  def disjoined(m: MSet[A]): Boolean

  def size: Int

  def matches(m: MSet[A]): Boolean

  def extract(m: MSet[A]): Option[MSet[A]]

  def asList: List[A]

  def asMap: Map[A, Int]

  def iterator: Iterator[A]

  def matchIterator: Iterator[(A, MSet[A])]

  def map[B](f: A => B):MSet[B]

  def flatMap[B](f: A => MSet[B]): MSet[B]

  def filter(f: A => Boolean): MSet[A]

  def collect[B](f: PartialFunction[A, B]): MSet[B]
}

object MSet {

  // Hidden reference implementation
  private case class MSetImpl[A](asMap: Map[A, Int]) extends MSet[A] {

    def this(list: List[A]) = this(list.groupBy(a => a).map((a, n) => (a, n.size)))

    // keeping both representations, as Map and as List
    override val asList: List[A] = asMap.toList.flatMap((a, n) => List.fill(n)(a))

    override def apply(elem: A): Int = asMap.getOrElse(elem, 0)

    override def disjoined(otherSet: MSet[A]): Boolean = (asList.intersect(otherSet.asList)).isEmpty

    override def diff(otherSet: MSet[A]): MSet[A] = new MSetImpl(asList.diff(otherSet.asList))

    override def extract(otherSet: MSet[A]): Option[MSet[A]] = Some(diff(otherSet)).filter(_.size == size - otherSet.size)

    override def union(otherSet: MSet[A]): MSet[A] = new MSetImpl(asList ++ otherSet.asList)

    override def matches(otherSet: MSet[A]): Boolean = extract(otherSet).isDefined

    override def iterator: Iterator[A] = asList.distinct.iterator

    override def matchIterator: Iterator[(A, MSet[A])] = iterator.map(a => (a, extract(MSet(a)).get))

    override def size: Int = asList.size

    override def map[B](f: A => B) = new MSetImpl[B](asList.map(f))

    override def flatMap[B](f: A => MSet[B]) = new MSetImpl[B](asList.flatMap(x => f(x).asList))

    override def filter(f: A => Boolean) = new MSetImpl[A](asList.filter(f))

    override def collect[B](f: PartialFunction[A, B]) = new MSetImpl[B](asList.collect(f))
  }

  def ofMap[A](m: Map[A, Int]): MSet[A] = MSetImpl(m)

  def ofList[A](l: List[A]): MSet[A] = new MSetImpl(l)

  def apply[A](l: A*): MSet[A] = new MSetImpl(l.toList)
}
