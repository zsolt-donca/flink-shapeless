package org.apache.flink.api.scala.derived.serializer.instances

import java.io.{DataInput, DataOutput}

import org.apache.flink.api.scala.derived.serializer._
import shapeless._

trait L05_Products extends L06_Coproducts {
  implicit def hnilSerializer: Serializer[HNil] = new ValueSerializer[HNil] {
    override def serializeNewValue(value: HNil, dataOutput: DataOutput, state: SerializationState): Unit = {}

    override def deserializeNewValue(dataInput: DataInput, state: DeserializationState): HNil = HNil
  }

  implicit def hlistSerializer[H, T <: HList](implicit headSer: Lazy[Serializer[H]], tailSer: Serializer[T]): Serializer[H :: T] = new ValueSerializer[H :: T] {
    override def serializeNewValue(value: H :: T, dataOutput: DataOutput, state: SerializationState): Unit = {
      value match {
        case head :: tail =>
          headSer.value.serialize(head, dataOutput, state)
          tailSer.serializeNewValue(tail, dataOutput, state)
      }
    }

    override def deserializeNewValue(dataInput: DataInput, state: DeserializationState): H :: T = {
      val head = headSer.value.deserialize(dataInput, state)
      val tail = tailSer.deserializeNewValue(dataInput, state)
      head :: tail
    }
  }
}
