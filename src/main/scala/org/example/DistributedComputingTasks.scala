package org.example
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import org.apache.spark.rdd.RDD

object DistributedComputingTasks {
  def main(args: Array[String] , x:Int): Unit = {

    // i assume that array contain the path to the file in the order of dataset A, B and C is the output
    val dataset_a = args(0)
    val dataset_b = args(1)
    val dataset_c = args(2)
    val limit_x = x // i assume that the limit is 10 as stated in the document

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Distributed Computing Tasks")
      .getOrCreate()

    import spark.implicits._

    // read the dataset
    val dfA = spark.read.parquet(dataset_a)
    val dfB = spark.read.parquet(dataset_b)

    // covert to RDD with the col that needed to do filter
    val rddA: RDD[(Long, Long, String)] = dfA.rdd.map(row =>
      (row.getAs[Long]("geographical_location_oid"),
        row.getAs[Long]("detection_oid"),
        row.getAs[String]("item_name"))
    )

    val rddB: RDD[(Long, String)] = dfB.rdd.map(row =>
      (row.getAs[Long]("geographical_location_oid"), row.getAs[String]("geographical_location"))
    )

    // remove the repeat row (point 2)
    val rddAUnique: RDD[(Long, Long, String)] = rddA.distinct()

    // Mapping 1 as key
    val mappedRdd: RDD[((Long, String), Int)] = rddAUnique.map {
      case (geoLocOid, _, itemName) => ((geoLocOid, itemName), 1)
    }

    // Reduce by key to count
    val countRdd: RDD[((Long, String), Int)] = mappedRdd.reduceByKey(_ + _)

    // group by
    val groupedRdd: RDD[((Long, String), Iterable[Int])] = countRdd
      .groupByKey()

    // dense_rank base on count
    val rankedRdd: RDD[(Long, String, Int)] = groupedRdd.flatMap {
      case ((geoLocOid, itemName), counts) =>
        val sortedCounts = counts.toList.sortBy(-_)
        sortedCounts.zipWithIndex.map {
          case (_, rank) =>
            (geoLocOid, itemName, rank + 1)
        }
    }

    // joining table
    val joinedRdd: RDD[( String, Int, String)] = rankedRdd
      .map { case (geoLocOid, itemName, itemRank) =>
        (geoLocOid, (itemName, itemRank))
      }
      .join(rddB)
      .map {
        case (geoLocOid, ((itemName, itemRank), geoLoc)) =>
          ( itemName, itemRank, geoLoc)
      }

    // sorting
    val sortedRdd: RDD[(String, Int, String)] = joinedRdd
      .map { case ( itemName, itemRank, geoLoc) =>
        (geoLoc,itemRank,itemName)
      }
      .sortBy(_._2)

    // limit the number of data
    val result = sortedRdd.take(limit_x)

    val resultDF = result.toSeq.toDF("geographical_location", "item_rank","item_name")
    resultDF.write.mode("Overwrite").parquet(dataset_c)
  }
}