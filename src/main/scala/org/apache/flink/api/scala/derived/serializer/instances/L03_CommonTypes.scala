package org.apache.flink.api.scala.derived.serializer.instances

import org.apache.flink.api.scala.derived.serializer.{RefSerializer, Serializer}

trait L03_CommonTypes extends L04_Collections {
  implicit def stringSerializer: Serializer[String] = RefSerializer(_.writeUTF(_), _.readUTF())
}
