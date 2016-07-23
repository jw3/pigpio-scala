organization := "com.github.jw3"
name := "pigpio-scala"
version := "1.1.40-SNAPSHOT"
licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion := "2.11.8"
scalacOptions += "-target:jvm-1.8"

resolvers += "jw3 at bintray" at "https://dl.bintray.com/jw3/maven"

libraryDependencies ++= {
  val akkaVersion = "2.4.8"
  val scalatestVersion = "3.0.0-M15"

  Seq(
    "com.rxthings" %% "rxgpio-pigpio" % "0.0.1-44fc295f66d473b84faca9cc6e59f0f9e5f6af8a-SNAPSHOT",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,

    "net.java.dev.jna" % "jna" % "4.2.1",
    "com.nativelibs4java" % "jnaerator-runtime" % "0.12",

    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion % Runtime,

    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "org.scalactic" %% "scalactic" % scalatestVersion % Test,
    "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
  )
}

com.updateimpact.Plugin.apiKey in ThisBuild := sys.env.getOrElse("UPDATEIMPACT_API_KEY", (com.updateimpact.Plugin.apiKey in ThisBuild).value)
