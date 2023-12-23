name := "AkkaActorTypedProject"

version := "0.1"

scalaVersion := "2.13.10"

val akkaver = "2.8.0"
val actorDependencies = Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaver,
//  "com.typesafe.akka" %% "akka-slf4j" % akkaver,
  "org.slf4j" % "slf4j-nop" % "2.0.5",
//  "ch.qos.logback" % "logback-classic" % "1.4.4"

  "com.typesafe.akka" %% "akka-actor" % "2.8.0",
  "com.typesafe.akka" % "akka-remote" % "2.4.12",
  "com.typesafe.akka" %% "akka-stream" % "2.8.0",
  "com.typesafe.akka" %% "akka-http" % "10.5.0")
// Add more Akka dependencies as required by your project

