package org.apache.flink.api.scala.derived.serializer.instances

import org.apache.flink.api.scala.derived.serializer.{Serializer, ValueSerializer}


trait L01_Primitives extends L02_Injections {
  implicit def booleanSerializer: Serializer[Boolean] = ValueSerializer(_.writeBoolean(_), _.readBoolean())

  implicit def byteSerializer: Serializer[Byte] = ValueSerializer(_.writeByte(_), _.readByte())

  implicit def shortSerializer: Serializer[Short] = ValueSerializer(_.writeShort(_), _.readShort())

  implicit def charSerializer: Serializer[Char] = ValueSerializer(_.writeChar(_), _.readChar())

  implicit def intSerializer: Serializer[Int] = ValueSerializer(_.writeInt(_), _.readInt())

  implicit def longSerializer: Serializer[Long] = ValueSerializer(_.writeLong(_), _.readLong())

  implicit def floatSerializer: Serializer[Float] = ValueSerializer(_.writeFloat(_), _.readFloat())

  implicit def doubleSerializer: Serializer[Double] = ValueSerializer(_.writeDouble(_), _.readDouble())

  implicit def unitSerializer: Serializer[Unit] = ValueSerializer((_, _) => {}, _ => ())
}
