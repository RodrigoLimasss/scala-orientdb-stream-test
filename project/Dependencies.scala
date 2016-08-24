import sbt._

object Dependencies{

  val commonDependencies : Seq[ModuleID] = Seq(
    "com.marekkadek" %% "orientdb-scala-stream" % "0.5.4"
  )

  val streamingDependencies : Seq[ModuleID] = commonDependencies
}