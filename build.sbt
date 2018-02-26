organization := "com.github.biopet"
organizationName := "Biopet"

startYear := Some(2014)

name := "FastqSplitter"
biopetUrlName := "fastq-splitter"

biopetIsTool := true

mainClass in assembly := Some("nl.biopet.tools.fastqsplitter.FastqSplitter")

developers := List(
  Developer(id = "ffinfo",
            name = "Peter van 't Hof",
            email = "pjrvanthof@gmail.com",
            url = url("https://github.com/ffinfo"))
)

scalaVersion := "2.11.11"

libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.3-SNAPSHOT" changing ()
libraryDependencies += "com.github.biopet" %% "ngs-utils" % "0.3"
libraryDependencies += "com.github.biopet" %% "tool-test-utils" % "0.2-SNAPSHOT" % Test changing ()
