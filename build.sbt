name := "SI-project-GA"

version := "1.0"

scalaVersion := "2.12.2"

publishArtifact in (Compile, packageBin) := true

// https://mvnrepository.com/artifact/org.scalafx/scalafx_2.12
libraryDependencies += "org.scalafx" % "scalafx_2.12" % "8.0.102-R11"
