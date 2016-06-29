val commonSettings = Seq(
  name := "JarSerialver",
  version := "1.0.0",
  scalaVersion := "2.11.8",
  assemblyJarName in assembly := "jarserialver.jar",
  mainClass in assembly := Some("com.wavesplatform.jarserialver.Application")
)

val dependencies = Seq(
  "org.scala-lang" % "scala-compiler" % "2.11.8",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",
  "com.github.scopt" %% "scopt" % "3.4.+"
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= dependencies
  )
