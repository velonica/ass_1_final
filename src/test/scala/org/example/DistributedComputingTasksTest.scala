package DistributedComputingTasksTest

import org.example.DistributedComputingTasks
import org.junit.Test
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.scalatest.BeforeAndAfter
import org.scalatest.FlatSpec

class DistributedComputingTasksTest extends FlatSpec with BeforeAndAfter {

  var spark: SparkSession = _

  before {
    spark = SparkSession.builder()
      .master("local[*]")
      .appName("DistributedComputingTasksTest")
      .getOrCreate()
  }

  after {
    // stop spark
    if (spark != null) {
      spark.stop()
    }
  }

  // path to file
  val dataset_a = "src/test/dataset_a/dataset_a.parquet"
  val dataset_b = "src/test/dataset_b/dataset_b.parquet"
  val dataset_c = "src/test/dataset_c/dataset_c.parquet"

  
  val x = 10
  val args = Array(dataset_a, dataset_b, dataset_c,x)

  DistributedComputingTasks.main(args)

  val outputDF = spark.read.parquet(dataset_c)

  // check the columns
  assert(outputDF.columns.contains("geographical_location"))
  assert(outputDF.columns.contains("item_rank"))
  assert(outputDF.columns.contains("item_name"))

  // check number of col is equal to x
  val rowCount = outputDF.count()
  assert(rowCount == x)

}
