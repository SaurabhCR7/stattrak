ThisBuild / version := "1.4.1"

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
  "ch.qos.logback" % "logback-classic" % "1.5.6",
  "net.dv8tion" % "JDA" % "5.0.1",
  "io.circe" %% "circe-parser" % "0.14.9",
  "io.circe" %% "circe-generic" % "0.14.7",
  "com.scylladb" % "java-driver-core" % "4.18.0.1",
  "com.lihaoyi" %% "requests" % "0.8.3"
)

scalacOptions += "-Xmax-inlines:50"