name := "Dencrypt"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.github.scala-incubator.io" % "scala-io-file_2.11" % "0.4.3-1",
  "org.xerial" % "sqlite-jdbc" % "3.8.6",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "com.typesafe.scala-logging" % "scala-logging-slf4j_2.11" % "2.1.2",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)

