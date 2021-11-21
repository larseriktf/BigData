import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

// Note: THIS FILE MIGHT NOT BE NEEDED

object PopulateCassandra {
    def main(args: Array[String]) {
        val csv = "datasets/timesData.csv"

        // Start spark session
        val spark = SparkSession.builder
            .config("spark.master", "local")
            .appName("Educational quality among countries")
            .getOrCreate();
        
        val universityDf = spark.read.format("csv").option("inferSchema", "true").option("header", "true").load(csv);
        
        universityDf.printSchema();

        // Stop spark session
        spark.stop();
    }
}