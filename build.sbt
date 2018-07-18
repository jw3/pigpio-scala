organization := "com.github.jw3"
name := "pigpio-scala"
version := "0.1-SNAPSHOT"
licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion := "2.11.8"
scalacOptions += "-target:jvm-1.8"

resolvers += "jw3 at bintray" at "https://dl.bintray.com/jw3/maven"

libraryDependencies ++= {
  val akkaVersion = "2.5.14"
  val scalatestVersion = "3.0.3"

  Seq(
    "com.github.jw3" % "pigpio-jna" % "0.3",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,

    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion % Runtime,

    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "org.scalactic" %% "scalactic" % scalatestVersion % Test,
    "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test
  )
}
