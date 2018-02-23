val ScalatraVersion = "2.6.2"

organization := "com.scalacourse"

name := "Tweet Tweet Web App"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.4"

resolvers += Classpaths.typesafeReleases
resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.scalatra" %% "scalatra-auth" % ScalatraVersion,
  "org.mindrot"  % "jbcrypt"   % "0.3m",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.8.v20171121" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5",
  "com.sun.jersey" % "jersey-core" % "1.8",
  "org.json4s" %% "json4s-jackson" % "3.5.2"
)

lazy val tweet_tweet = (project in file("."))
  .enablePlugins(JettyPlugin)
  .enablePlugins(SbtTwirl)
  .enablePlugins(ScalatraPlugin)
