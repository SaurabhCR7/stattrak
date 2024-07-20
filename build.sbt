ThisBuild / version := "1.5.0"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "stattrak",
    assembly / mainClass := Some("com/stattrak/Main")
  )

ThisBuild / assemblyMergeStrategy := {
  case "META-INF/services/org.slf4j.impl.StaticLoggerBinder" => MergeStrategy.first
  case "META-INF/logback.xml" => MergeStrategy.first
  case PathList("META-INF", xs @ _*) =>
    xs map {_.toLowerCase} match {
      case "manifest.mf" :: Nil => MergeStrategy.discard
      case _ => MergeStrategy.first
    }
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "net.dv8tion" % "JDA" % "5.0.1",
  "org.slf4j" % "slf4j-api" % "2.0.12",
  "io.circe" %% "circe-parser" % "0.14.9",
  "io.circe" %% "circe-generic" % "0.14.7",
  "com.scylladb" % "java-driver-core" % "4.18.0.1",
  "com.lihaoyi" %% "requests" % "0.8.3",
  "ch.qos.logback" % "logback-classic" % "1.5.6"
)

scalacOptions += "-Xmax-inlines:50"