name := "timeseries"

version := "0.1"

scalaVersion := "2.10.2"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/repo"

resolvers += "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

scalacOptions in Test ++= Seq("-Yrangepos")

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies ++=  Seq(
        "org.reactivemongo" %% "reactivemongo" % "0.10-SNAPSHOT",
        "joda-time" % "joda-time" % "2.1",
        "org.joda" % "joda-convert" % "1.2",
        "org.specs2" %% "specs2" % "2.2.3" % "test")
