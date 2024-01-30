ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "stattrak",
    idePackagePrefix := Some("com.stattrak")
  )

libraryDependencies ++= Seq(
  "net.dv8tion" % "JDA" % "5.0.0-beta.13",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "ch.qos.logback" % "logback-classic" % "1.4.12",
  "org.slf4j" % "slf4j-api" % "2.0.7",
  "com.github.ben-manes.caffeine" % "caffeine" % "3.1.8",
  "io.circe" %% "circe-parser" % "0.14.5",
  "com.scylladb" % "java-driver-core" % "4.15.0.1"
)