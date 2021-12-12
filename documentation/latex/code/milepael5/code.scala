val myDf = spark.read.option("header","true").csv("student-performance.csv")
myDf.write.format("parquet").option("header","true").mode("overwrite")
.save("student-performance.parquet")
val PDF = spark.read.option("header","true").parquet("student-performance.parquet")
