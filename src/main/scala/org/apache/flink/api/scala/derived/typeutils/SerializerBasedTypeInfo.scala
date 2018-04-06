/*
 * Copyright 2017 Georgi Krastev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.flink
package api.scala.derived.typeutils

import org.apache.flink.api.common.ExecutionConfig
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.common.typeutils.TypeSerializer
import org.apache.flink.api.scala.derived.serializer.Serializer

import scala.reflect.ClassTag

class SerializerBasedTypeInfo[P](implicit val serializer: Serializer[P], tag: ClassTag[P])
  extends TypeInformation[P] {

  def isBasicType: Boolean = false

  def isTupleType: Boolean = false

  def isKeyType: Boolean = false

  def getArity: Int = 0

  def getTotalFields: Int = getArity

  def getTypeClass: Class[P] =
    tag.runtimeClass.asInstanceOf[Class[P]]

  def createSerializer(config: ExecutionConfig): TypeSerializer[P] = new SerializerBasedTypeSerializer[P](serializer)

  def canEqual(that: Any): Boolean =
    that.isInstanceOf[SerializerBasedTypeInfo[_]]

  override def equals(other: Any): Boolean = other match {
    case that: SerializerBasedTypeInfo[_] =>
      (this eq that) || (that canEqual this) && this.serializer == that.serializer
    case _ => false
  }

  override def hashCode: Int =
    serializer.##

  override def toString: String = s"${getTypeClass.getTypeName}"
}
