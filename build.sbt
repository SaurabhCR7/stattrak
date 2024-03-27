ThisBuild / version := "1.1.1"

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
  "ch.qos.logback" % "logback-classic" % "1.5.3",
  "net.dv8tion" % "JDA" % "5.0.0-beta.21",
  "io.circe" %% "circe-parser" % "0.14.6",
  "io.circe" %% "circe-generic" % "0.14.6",
  "com.scylladb" % "java-driver-core" % "4.17.0.0"
)

scalacOptions += "-Xmax-inlines:50"