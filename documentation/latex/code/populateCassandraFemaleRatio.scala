// First aggregation
val universityByFemaleRatio = newUniversityDf
  .select(col("id"), col("country"), col("university_name"), split(col("female_male_ratio"), " : ")
  .getItem(0).cast("int").as("female_ratio"))
  .where(col("year") === 2016)
  .orderBy(desc_nulls_last("female_ratio")).limit(10)

// Write to Cassandra
universityByFemaleRatio.write.format("org.apache.spark.sql.cassandra")
  .option("table","female_ratio")
  .option("keyspace","university")
  .option("confirm.truncate", "true")
  .mode("Overwrite").save()