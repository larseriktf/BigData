// First join
val dataForResult = myDf.select(col("Fedu"), col("Medu"), col("G1"), col("G2"), col("G3")).persist(MEMORY_ONLY)
//framen can now be shipped to all executors
val broadcastRes = broadcast(dataForResult)
//actions to trigger persistence
val result1 = broadcastRes.groupBy("Fedu").agg(expr(" avg(G1) as FirstYear"), expr( " avg(G2) as SecondYear"),expr( " avg(G3) as ThirdYear"))
val result2 = broadcastRes.groupBy("Medu").agg(expr(" avg(G1) as FirstYear"), expr( " avg(G2) as SecondYear"),expr( " avg(G3) as ThirdYear"))

//join on every "id"
val joined = result1.joinWith(result2, result1("Fedu") === result2("Medu"))

//transform
val temp = joined.selectExpr("_1 as Father", "_2 as Mother")
val temp2 = temp.select(col("Father.*"),col("Mother.*"))
val flattenTemp = temp2.toDF("FatherEdu","FFirstYear","FSecondYear","FThirdYear", "MotherEdu","MFirstYear","MSecondYear","MThirdYear")

flattenTemp.write.format("org.apache.spark.sql.cassandra").option("table","parents").option("keyspace","educationTest").option("confirm.truncate", "true").mode("Overwrite").save()
val dataForResult = dataForResult.unpersist()

val loadedCas = spark.read.format("org.apache.spark.sql.cassandra").option("table", "parents").option("keyspace", "edu").load()

