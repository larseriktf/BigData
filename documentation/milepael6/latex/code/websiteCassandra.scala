// First aggregation
val universityByFemaleRatioDf = spark.read.format("org.apache.spark.sql.cassandra")
  .option("table","female_ratio")
  .option("keyspace","university").load()

universityByFemaleRatioDf
  .repartition(1).write.mode("overwrite")
  .json("components/json/university_by_female_ratio.json")

// Second aggregation
val universityByMaleRatioDf = spark.read.format("org.apache.spark.sql.cassandra")
  .option("table","male_ratio")
  .option("keyspace","university").load()

universityByMaleRatioDf
  .repartition(1).write.mode("overwrite")
  .json("components/json/university_by_male_ratio.json")

// Third aggregation
val averageScorePerYearDf = spark.read.format("org.apache.spark.sql.cassandra")
  .option("table","average_score_per_year")
  .option("keyspace","university").load()

averageScorePerYearDf
  .repartition(1).write.mode("overwrite")
  .json("components/json/average_score_per_year.json")