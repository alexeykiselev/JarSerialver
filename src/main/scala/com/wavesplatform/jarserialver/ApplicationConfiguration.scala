package com.wavesplatform.jarserialver

import java.io.File

case class ApplicationConfiguration(
                                     jarFile: File = new File("jarserialver.jar"),
                                     packageName: String = "",
                                     classpath: String = ".")
