package org.apache.flink.api.scala.derived.serializer.instances

import java.io.{DataInput, DataOutput}

import org.apache.flink.api.scala.derived.serializer.{DeserializationState, RefSerializer, SerializationState, Serializer}
import shapeless._

trait L07_Generics {

  implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], ser: Lazy[Serializer[R]]): Serializer[A] = new RefSerializer[A] {

    override def serializeNewValue(value: A, dataOutput: DataOutput, state: SerializationState): Unit = {
      val r = gen.to(value)
      ser.value.serializeNewValue(r, dataOutput, state)
    }

    override def deserializeNewValue(dataInput: DataInput, state: DeserializationState): A = {
      val r = ser.value.deserializeNewValue(dataInput, state)
      gen.from(r)
    }
  }

}
