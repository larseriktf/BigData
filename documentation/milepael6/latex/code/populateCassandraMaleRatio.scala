// Second aggregation
val universityByMaleRatio = newUniversityDf
  .select(col("id"), col("country"), col("university_name"), split(col("female_male_ratio"), " : ")
  .getItem(1).cast("int").as("male_ratio"))
  .where(col("year") === 2016)
  .orderBy(desc_nulls_last("male_ratio")).limit(10)

// Write to Cassandra
universityByMaleRatio.write.format("org.apache.spark.sql.cassandra")
  .option("table","male_ratio")
  .option("keyspace","university")
  .option("confirm.truncate", "true")
  .mode("Overwrite").save()
