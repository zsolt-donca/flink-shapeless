package org.apache.flink.api.scala.derived.serializer.instances

import java.io.{DataInput, DataOutput}

import org.apache.flink.api.scala.derived.serializer._
import shapeless._

trait L06_Coproducts extends L07_Generics {
  implicit def cnilSerializer: Serializer[CNil] = new ValueSerializer[CNil] {
    override def serializeNewValue(value: CNil, dataOutput: DataOutput, state: SerializationState): Unit = {
      sys.error("Impossible")
    }

    override def deserializeNewValue(dataInput: DataInput, state: DeserializationState): CNil = {
      sys.error("Impossible")
    }
  }

  implicit def coproductSerializer[H, T <: Coproduct](implicit headSer: Lazy[Serializer[H]], tailSer: Serializer[T]): Serializer[H :+: T] = new ValueSerializer[H :+: T] {
    override def serializeNewValue(value: H :+: T, dataOutput: DataOutput, state: SerializationState): Unit = {
      value match {
        case Inl(head) =>
          dataOutput.writeInt(state.readeAndResetCoproductCases)
          headSer.value.serializeNewValue(head, dataOutput, state)
        case Inr(tail) =>
          state.increaseCoproductCases()
          tailSer.serializeNewValue(tail, dataOutput, state)
      }
    }

    override def deserializeNewValue(dataInput: DataInput, state: DeserializationState): H :+: T = {
      if (state.withoutCoproductCases) {
        state.setCoproductCases(dataInput.readInt())
      }

      if (state.decreaseAndCheckCoproductCases) {
        Inl(headSer.value.deserializeNewValue(dataInput, state))
      } else {
        Inr(tailSer.deserializeNewValue(dataInput, state))
      }
    }
  }


}
