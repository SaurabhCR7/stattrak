ThisBuild / version := "1.3.0"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "stattrak",
    assembly / mainClass := Some("com/stattrak/Main")
  )

ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "net.dv8tion" % "JDA" % "5.0.0-beta.21",
  "ch.qos.logback" % "logback-classic" % "1.5.6",
  "io.circe" %% "circe-parser" % "0.14.7",
  "io.circe" %% "circe-generic" % "0.14.7",
  "com.scylladb" % "java-driver-core" % "4.17.0.0",
  "com.lihaoyi" %% "requests" % "0.8.3"
)

scalacOptions += "-Xmax-inlines:50"