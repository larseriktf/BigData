val result1 = PDF.groupBy("Fedu").agg(expr(" avg(G1) as FirstYear"), 
expr( " avg(G2) as SecondYear"),expr( " avg(G3) as ThirdYear"))

val result2 = PDF.groupBy("Medu").agg(expr(" avg(G1) as FirstYear"), 
expr( " avg(G2) as SecondYear"),expr( " avg(G3) as ThirdYear"))
//join on the number value of Father and Mother Education [0-4]
val joined = result1.joinWith(result2, result1("Fedu") === result2("Medu"))
//set correct name on right column
val temp = joined.selectExpr("_1 as Father", "_2 as Mother")
//fetch the table
val temp2 = temp.select(col("Father.*"),col("Mother.*"))
//flatten to dataframe
val flattenTemp = temp2.toDF("FatherEdu","FFirstYear","FSecondYear","FThirdYear", 
"MotherEdu","MFirstYear","MSecondYear","MThirdYear")
flattenTemp.write.format("csv").option("header","true").mode("overwrite")
.save("grades-average.csv")
