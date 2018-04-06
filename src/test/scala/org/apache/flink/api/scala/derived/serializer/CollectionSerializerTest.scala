package org.apache.flink.api.scala.derived.serializer

import org.scalatest.PropSpec

class CollectionSerializerTest extends PropSpec with RefSerializerHelper {
  property("Lists of ints are serialized") {
    forAllRoundTrip[List[Int]]()
  }

  property("Vectors of ints are serialized") {
    forAllRoundTrip[Vector[Int]]()
  }

  property("Sets of strings are serialized") {
    forAllRoundTrip[Vector[String]]()
  }

  //  property("Arrays of longs are serialized") {
  //    forAllRoundTrip[Array[Long]]()
  //  }

  property("Vectors of products are serialized") {
    case class Test(i: Int, c: Char)
    forAllRoundTrip[Vector[Test]]()
  }

  property("Vectors of coproducts are serialized") {
    sealed trait Coproduct
    case class CaseOne(i: Int) extends Coproduct
    case class CaseTwo() extends Coproduct
    case object CaseThree extends Coproduct
    forAllRoundTrip[Vector[Coproduct]]()
  }

}
