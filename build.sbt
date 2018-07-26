enablePlugins(GitVersioning)

organization := "com.github.jw3"
name := "pigpio-scala"
git.useGitDescribe := true
scalacOptions ++= Seq(
  "-encoding", "UTF-8",

  "-feature",
  "-unchecked",
  "-deprecation",

  "-language:postfixOps",
  "-language:implicitConversions",

  "-Ywarn-unused-import",
  //"-Xfatal-warnings",
  "-Xlint:_"
)

scalaVersion := "2.12.6"
resolvers += Resolver.bintrayRepo("jw3", "maven")
licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

libraryDependencies ++= {
  val akkaVersion = "2.5.14"
  val scalatestVersion = "3.0.3"

  Seq(
    "com.github.jw3" % "pigpio-jna" % "0.6",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,

    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion % Runtime,

    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "org.scalactic" %% "scalactic" % scalatestVersion % Test,
    "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test
  )
}

com.updateimpact.Plugin.apiKey in ThisBuild := sys.env.getOrElse("UPDATEIMPACT_API_KEY", (com.updateimpact.Plugin.apiKey in ThisBuild).value)
