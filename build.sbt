name := "ass_1_final"

version := "0.1"

scalaVersion := "2.12.10"  // Use the appropriate Scala version that works with Spark

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.3.2",  // Apache Spark core library
  "org.apache.spark" %% "spark-sql" % "3.3.2",   // Apache Spark SQL library
  "org.apache.spark" %% "spark-hive" % "3.3.2"   // Optional: Spark Hive support if needed
  "org.apache.hadoop" % "hadoop-client-api" % "3.3.2" // Hadoop Client API

)

mainClass in Compile := Some("org.example.DistributedComputingTasks") 
