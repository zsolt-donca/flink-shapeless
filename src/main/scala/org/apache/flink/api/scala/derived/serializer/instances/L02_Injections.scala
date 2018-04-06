package org.apache.flink.api.scala.derived.serializer.instances

import java.io.{DataInput, DataOutput}

import org.apache.flink.api.scala.derived.serializer.{DeserializationState, RefSerializer, SerializationState, Serializer}
import org.apache.flink.api.scala.derived.typeutils.Inject

trait L02_Injections extends L03_CommonTypes {
  implicit def injectSerializer[T, U](implicit inj: Inject[T, U], ser: Serializer[U]): Serializer[T] = new RefSerializer[T] {
    override def serializeNewValue(value: T, dataOutput: DataOutput, state: SerializationState): Unit = {
      ser.serialize(inj(value), dataOutput, state)
    }

    override def deserializeNewValue(dataInput: DataInput, state: DeserializationState): T = {
      val u = ser.deserialize(dataInput, state)
      inj.invert(u)
    }
  }
}
