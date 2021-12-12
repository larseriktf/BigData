val PDF = spark.read.option("header","true")
.option("inferSchema","true").load("hdfs:///country-profiles.parquet")
val PDF = spark.read.option("header","true").parquet("hdfs://localhost:9000/filepath")
(.csv() kommando hvis fil er av type CSV)
