ThisBuild / version := "1.0.0"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "stattrak"
  )

libraryDependencies ++= Seq(
  "net.dv8tion" % "JDA" % "5.0.0-beta.20",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "ch.qos.logback" % "logback-classic" % "1.4.12",
  "io.circe" %% "circe-parser" % "0.14.5",
  "io.circe" %% "circe-generic" % "0.14.5",
  "com.scylladb" % "java-driver-core" % "4.15.0.1"
)

scalacOptions += "-Xmax-inlines:50"