//grupper etter studytime
val temp = PDF.groupBy("studytime").agg(expr(" avg(G1) as FirstYear"),
 expr( " avg(G2) as SecondYear"),expr( " avg(G3) as ThirdYear"))
//gi int verdier i kolonne meningsfull string verdi
val setString = udf {(studytime: Integer) => if(studytime == 1) 
"Very little" else if(studytime == 2) "Little" else if( studytime == 3)
 "Medium" else if( studytime == 4) "Much" else "Very Much"} 
val temp2 = temp.withColumn("studytime", setString(temp("studytime")))
temp2.write.format("csv").option("header","true").mode("overwrite")
.save("studytime-grades.csv")
