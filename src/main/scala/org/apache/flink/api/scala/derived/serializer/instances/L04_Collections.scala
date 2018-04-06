package org.apache.flink.api.scala.derived.serializer.instances

import java.io.{DataInput, DataOutput}

import org.apache.flink.api.scala.derived.serializer.{DeserializationState, RefSerializer, SerializationState, Serializer}

import scala.collection.generic.CanBuild
import scala.language.higherKinds

trait L04_Collections extends L05_Products {

  implicit def traversableSerializer[C[e] <: Traversable[e], T](implicit cb: CanBuild[T, C[T]], ser: Serializer[T]): Serializer[C[T]] = new RefSerializer[C[T]] {
    override def serializeNewValue(value: C[T], dataOutput: DataOutput, state: SerializationState): Unit = {
      dataOutput.writeInt(value.size)
      value.foreach(t => ser.serialize(t, dataOutput, state))
    }

    override def deserializeNewValue(dataInput: DataInput, state: DeserializationState): C[T] = {
      val b = cb()
      val size = dataInput.readInt()
      b.sizeHint(size)
      for (i <- 1 to size) {
        val t = ser.deserialize(dataInput, state)
        b += t
      }
      b.result()
    }
  }
}
