package it.unibo.pc.multiset

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.*

class MSetSpec extends AnyFlatSpec with Matchers {

  "An empty MSet" should "have size 0" in {
    MSet() should have size 0
  }

  "A MSet" should "be equal to another with just different ordering of elements" in {
    MSet(10, 20, 30, 30, 15, 15) shouldBe MSet(10, 20, 30, 15, 30, 15)
  }

  "A MSet" should "not be equal to another when adding s repetition" in {
    MSet(10, 20, 30, 30, 15, 15) should not be MSet(10, 20, 30, 15, 30, 15, 5, 5)
  }

  "A MSet" should "be equally constructed as List or as Map" in {
    MSet(10, 20, 30, 30, 15, 15) shouldBe MSet.ofMap(Map(10 -> 1, 20 -> 1, 30 -> 2, 15 -> 2))
  }
}
