val universityByFemaleRatio = universityPDf.select(col("country"), col("university_name"), split(col("female_ratio"), " : ")
  .getItem(0).cast("int").as("female_ratio")).distinct()
  .sort(desc_nulls_last("female_ratio")).limit(10)

universityByFemaleRatio.repartition(1).write.format("csv").option("header", "true").mode("overwrite").save("components/university_by_female_ratio)