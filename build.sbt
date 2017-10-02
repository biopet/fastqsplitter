organization := "com.github.biopet"
name := "fastq-splitter"

scalaVersion := "2.11.11"

resolvers += Resolver.mavenLocal

lazy val fastqSplitter = project in file(".")

libraryDependencies += "com.github.biopet" %% "biopet-tool-utils" % "0.1.0-SNAPSHOT"
libraryDependencies += "com.github.biopet" %% "biopet-ngs-utils" % "0.1.0-SNAPSHOT"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % Test
libraryDependencies += "org.testng" % "testng" % "6.8" % Test

mainClass in assembly := Some("nl.biopet.tools.fastqsplitter.Main")
