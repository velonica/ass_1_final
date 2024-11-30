name := "ass_1_final"

version := "0.1"

scalaVersion := "2.12.15"  // Use the appropriate Scala version that works with Spark

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.2.1",  // Apache Spark core library
  "org.apache.spark" %% "spark-sql" % "3.2.1",   // Apache Spark SQL library
  "org.apache.spark" %% "spark-hive" % "3.2.1"   // Optional: Spark Hive support if needed
)
