import sbt._

object Dependencies{

  val neo4jVersion = "2.3.2"

  val commonDependencies : Seq[ModuleID] = Seq(
    //"org.neo4j" % "neo4j" % neo4jVersion
  )

  val streamingDependencies : Seq[ModuleID] = commonDependencies
}