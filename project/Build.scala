import sbt.Keys._
import sbt._

object CustomBuild extends Build {

  val NamePrefix = "scala.orientdb.stream.test"

  name := NamePrefix + "."

  lazy val wrapper = Project(
    id = "orientdb-service",
    base = file("orientdb-service")
  ).settings(Common.settings: _*)
    .settings(mainClass in Compile := Some("orientdb-service.Main"))
    .settings(libraryDependencies ++= Dependencies.streamingDependencies)

  fork in run := true
}