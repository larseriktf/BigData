// Third aggregation
val averageScorePerYear = newUniversityDf.groupBy(col("year"))
  .agg(mean(col("total_score")).as("average_score"))
  .orderBy(desc("year"))

// Write to Cassandra
averageScorePerYear.write.format("org.apache.spark.sql.cassandra")
  .option("table","average_score_per_year")
  .option("keyspace","university")
  .option("confirm.truncate", "true")
  .mode("Overwrite").save()