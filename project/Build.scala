import sbt.Keys._
import sbt._

object CustomBuild extends Build {

  val NamePrefix = "com.scala.project.example"

  name := NamePrefix + "."

  lazy val wrapper = Project(
    id = "myModule",
    base = file("myModule")
  ).settings(Common.settings: _*)
    .settings(mainClass in Compile := Some("myModule.Main"))
    .settings(libraryDependencies ++= Dependencies.streamingDependencies)

  fork in run := true
}