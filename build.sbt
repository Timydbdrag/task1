name := "hometask1"

version := "0.1"

scalaVersion := "2.13.5"

val circeVersion = "0.14.0-M4"

ThisBuild / scalacOptions ++= Seq(
  "-Ymacro-annotations"
)

libraryDependencies ++= Seq(
  "io.circe"  %% "circe-core"     % circeVersion,
  "io.circe"  %% "circe-generic"  % circeVersion,
  "io.circe"  %% "circe-parser"   % circeVersion,
  "io.circe"  %% "circe-literal"  % circeVersion

)
// https://mvnrepository.com/artifact/io.circe/circe-generic-extras
libraryDependencies += "io.circe" %% "circe-generic-extras" % "0.13.1-M4"
