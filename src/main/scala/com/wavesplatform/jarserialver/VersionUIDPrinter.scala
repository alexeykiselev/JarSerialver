package com.wavesplatform.jarserialver

import java.io.{InputStream, ObjectInputStream}

class SerialVersionUIDInputStream(in: InputStream) extends ObjectInputStream(in) {
  def readVersionUID(): (String, Long) = {
    val descriptor = super.readClassDescriptor

    (descriptor.getName, descriptor.getSerialVersionUID)
  }
}
