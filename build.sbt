name := "ass_1_final"

version := "0.1"

scalaVersion := "2.12.10"  

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.3.2",  // Apache Spark core library
  "org.apache.spark" %% "spark-sql" % "3.3.2",   // Apache Spark SQL library
  "org.apache.spark" %% "spark-hive" % "3.3.2",   // Optional: Spark Hive support if needed
  "org.apache.hadoop" % "hadoop-client-api" % "3.3.2", // Hadoop Client API ( not avaliable)
 "org.apache.hadoop" % "hadoop-client-api" % "3.3.1", // Hadoop Client API
  "org.apache.hadoop" % "hadoop-client" % "3.3.2",// Hadoop Client 
  "org.apache.hadoop" % "hadoop-common" % "3.3.2" // Hadoop Common 

)

mainClass in Compile := Some("org.example.DistributedComputingTasks") 
