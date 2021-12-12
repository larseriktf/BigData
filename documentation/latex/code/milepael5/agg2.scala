//grupperer etter fritid og bytter navn til kolonnene
val temp = PDF.groupBy("freetime").agg(expr(" avg(G1) as FirstYear"), 
expr( " avg(G2) as SecondYear"),expr( " avg(G3) as ThirdYear"))
//bytter int verdiene til strenger som representerer verdiene
val setString = udf {(freetime: Integer) => if(freetime == 1) 
"Very little" else if(freetime == 2) "Little" else if(freetime == 3) 
"Medium" else if(freetime == 4) "Much" else "Very Much"} 
val temp2 = temp.withColumn("freetime", setString(temp("freetime")))
