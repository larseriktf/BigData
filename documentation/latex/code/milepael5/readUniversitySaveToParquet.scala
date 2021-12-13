// Read raw data and save to parquet
val universityDf = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("datasets/timesData.csv")
universityDf.write.format("parquet").mode("errorIfExists")
  .save("parquets/university_write.parquet")