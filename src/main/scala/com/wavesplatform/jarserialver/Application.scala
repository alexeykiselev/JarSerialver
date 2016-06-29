package com.wavesplatform.jarserialver

import java.io.{File, ObjectStreamClass}
import java.net.URLClassLoader
import java.util.jar.JarFile

import scopt.OptionParser
import sun.net.www.ParseUtil

import scala.collection.JavaConverters._
import scala.util.Try

object Application extends App {
  override def main(args: Array[String]) {
    val parser = new OptionParser[ApplicationConfiguration]("jarserialver") {
      head("jarserialver - list all serializable classes with SerialVersionUIDs", "v1.0.0")
      opt[File]('f', "file") required() valueName "<jar-file>" action { (x, c) =>
        c.copy(jarFile = x)
      } validate { x =>
        if (x.exists()) success else failure(s"Failed to open file $x")
      } text "JAR file"
      opt[String]('p', "package") valueName "<package-name>" action { (x, c) =>
        c.copy(packageName = x)
      } text "package name in classpath"
      opt[String]('c', "classpath") required() valueName "<classpath>" action { (x, c) =>
        c.copy(classpath = x)
      } text "classpath to look for jar and dependencies"
      help("help") text "display this help message"
    }

    parser.parse(args, ApplicationConfiguration()) match {
      case Some(config) =>
        val classLoader = getClassLoader(config.classpath)
        val classes = getJarEntries(config.jarFile, config.packageName)


        classes.foreach(c => {
          val uidOption = getClassVersionUID(c, classLoader)
          if (uidOption.isDefined && uidOption.get != 0) println(s"$c: ${uidOption.get}L")
        })
      case None =>
        parser.showTryHelp()
    }
  }

  def getJarEntries(jarFile: File, packageName: String): Seq[String] = {
    val jar = new JarFile(jarFile)

    jar.entries.asScala.map(e => e.getName)
      .filter(n => n.endsWith(".class"))
      .map(n => n.replace('/', '.'))
      .filter(n => n.startsWith(packageName))
      .map(n => n.substring(0, n.lastIndexOf(".class"))).toSeq
  }

  def getClassVersionUID(className: String, classLoader: ClassLoader): Option[Long] = {
    val cl = Try(Class.forName(className, false, classLoader))
    if (cl.isSuccess) {
      val desc = ObjectStreamClass.lookup(cl.get)
      if (desc != null) Some(desc.getSerialVersionUID) else None
    } else None
  }

  def getClassLoader(classpath: String): URLClassLoader = {
    val urls = classpath.split(File.pathSeparator)
      .map(t => ParseUtil.fileToEncodedURL(new File(new File(t).getCanonicalPath)))

    new URLClassLoader(urls)
  }

}
