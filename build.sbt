Global / onChangedBuildSource := ReloadOnSourceChanges

name := "PC-2122-scala3"
version := "0.1"
organization := "it.unibo.pc"
scalaVersion := "3.1.0"

semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision
scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

val scalaTest = Seq(
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scalatest" %% "scalatest" % "3.2.10" % "test",
)
val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.15.4" % "test"
val scalaChart = "de.sciss" %% "scala-chart" % "0.8.0"
val scalaCollections = ("org.scala-lang.modules" %% "scala-collection-contrib" % "0.2.2").cross(CrossVersion.for3Use2_13)
val scalaParallel = "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"

libraryDependencies ++= scalaTest ++ Seq(scalaCheck, scalaChart, scalaCollections, scalaParallel)

scalacOptions ++= Seq("-language:implicitConversions")
