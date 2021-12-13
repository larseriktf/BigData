val universityByMaleRatio = universityPDf.select(col("country"), col("university_name"), split(col("male_ratio"), " : ")
  .getItem(1).cast("int").as("male_ratio")).distinct()
  .sort(desc_nulls_last("male_ratio")).limit(10)

universityByMaleRatio.repartition(1).write.format("csv").option("header", "true").mode("overwrite").save("components/university_by_male_ratio")