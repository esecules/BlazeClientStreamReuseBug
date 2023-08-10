ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

//libraryDependencies ++= Seq(
//  "org.http4s" %% "http4s-blaze-server" % "0.23.13",
//  "org.http4s" %% "http4s-dsl" % "0.23.13",
//  "org.http4s" %% "http4s-blaze-client" % "0.23.13",
//  "org.typelevel" %% "cats-effect" % "3.5.0",
//  "co.fs2" %% "fs2-core" % "3.7.0"
//)

lazy val commonSettings = Seq(
  scalaVersion := "2.13.6",
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-effect" % "3.5.0",
    "co.fs2" %% "fs2-core" % "3.1.6"
  )
)

lazy val server = (project in file("server"))
  .settings(commonSettings: _*)
  .settings(
    name := "RandomDataStreamServer",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % "0.23.6",
      "org.http4s" %% "http4s-dsl" % "0.23.6"
    )
  )

lazy val client = (project in file("client"))
  .settings(commonSettings: _*)
  .settings(
    name := "RandomDataStreamClient",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-client" % "0.23.6"
    )
  )

lazy val root = (project in file("."))
  .aggregate(server, client)