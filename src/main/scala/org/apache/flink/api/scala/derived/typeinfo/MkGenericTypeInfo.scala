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
package api.scala.derived.typeinfo

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala.derived.serializer.Serializer
import org.apache.flink.api.scala.derived.typeutils._

import scala.reflect.ClassTag

/** Holder of a generic `TypeInformation` instance. */
sealed trait MkGenericTypeInfo[A] extends (() => TypeInformation[A]) with Serializable

/** Implicit derivation of generic `TypeInformation`. */
object MkGenericTypeInfo {

  /** Creates `TypeInformation` for a type having a Serializer instance */
  implicit def mkTypeInfo[P: Serializer : ClassTag]: MkGenericTypeInfo[P] = new MkGenericTypeInfo[P] {
    def apply: TypeInformation[P] = new SerializerBasedTypeInfo[P]
  }
}
