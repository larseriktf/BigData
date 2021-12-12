// Raw data
val universityDf = spark.read.format("csv")
    .option("header", "true")
    .option("inferSchema", "true")
    .load("datasets/timesData.csv")

// Write to Cassandra
universityDf.write.format("org.apache.spark.sql.cassandra")
    .option("table","university_rating")
    .option("keyspace","university")
    .option("confirm.truncate", "true")
    .mode("Overwrite").save()

// Load dataframe from Cassandra
val newUniversityDf = spark.read.format("org.apache.spark.sql.cassandra")
    .option("table","university_rating")
    .option("keyspace","university").load()