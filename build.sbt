ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.23"
lazy val root = (project in file("."))
  .settings(
    name := "Orders_Discount_Rule_Engine"
  )
