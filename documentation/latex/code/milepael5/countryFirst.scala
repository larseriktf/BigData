//Hente ut kolonner vi vil jobbe med
val temp = myDf.select("country", "Population in thousands (2017)", 
"GDP: Gross domestic product (million current US$)",
"GDP per capita (current US$)","Unemployment (% of labour force)",
"Population growth rate (average annual %)","Urban population (% of total population)_x",
"Urban population growth rate (average annual %)","Health: Total expenditure (% of GDP)",
"Education: Government expenditure (% of GDP)",
"Individuals using the Internet (per 100 inhabitants)","Quality Of Life Index",
"Purchasing Power Index","Safety Index","Health Care Index","Property price to income ratio",
"Affordability Index","Cost Of Living Index","Cost Of Living Plus Rent Index",
"Life expectancy at birth, total (years)","Military expenditure (% of GDP)", 
"Tax revenue (% of GDP)")
//skriv til ny parquet fil
temp.write.option("header","true")
.mode("overwrite").parquet("country-profiles-trimmed.parquet")
//sikre datatyper
val customSchema = StructType(Array(StructField("country",StringType, true),
StructField("Population in thousands (2017)", IntegerType, true),
StructField("GDP: Gross domestic product (million current US$)", DoubleType, true),
StructField("GDP per capita (current US$)", DoubleType, true),
StructField("Unemployment (% of labour force)", DoubleType, true),
StructField("Population growth rate (average annual %)", DoubleType, true),
StructField("Urban population (% of total population)_x", DoubleType, true),
StructField("Urban population growth rate (average annual %)", DoubleType, true),
StructField("Health: Total expenditure (% of GDP)", DoubleType, true),
StructField("Education: Government expenditure (% of GDP)", DoubleType, true),
StructField("Individuals using the Internet (per 100 inhabitants)", IntegerType, true),
StructField("Quality Of Life Index", DoubleType, true),
StructField("Purchasing Power Index", DoubleType, true),
StructField("Safety Index", DoubleType, true),
StructField("Health Care Index", DoubleType,true),
StructField("Property price to income ratio", DoubleType,true),
StructField("Affordability Index", DoubleType, true),
StructField("Cost Of Living Index", DoubleType, true),
StructField("Cost Of Living Plus Rent Index", DoubleType, true),
StructField("Life expectancy at birth, total (years)", DoubleType, true),
StructField("Military expenditure (% of GDP)", DoubleType, true),
StructField("Tax revenue (% of GDP)", DoubleType,true))
//les fra parquet med custom schema
val myDf = spark.read.option("header","true").option("customSchema", "true")
.parquet("country-profiles.parquet")
//sett opp kolonnenavn for bytte
val oldCol = Seq("country", "Population in thousands (2017)", 
"GDP: Gross domestic product (million current US$)",
"GDP per capita (current US$)","Unemployment (% of labour force)",
"Population growth rate (average annual %)","Urban population (% of total population)_x",
"Urban population growth rate (average annual %)","Health: Total expenditure (% of GDP)",
"Education: Government expenditure (% of GDP)",
"Individuals using the Internet (per 100 inhabitants)","Quality Of Life Index",
"Purchasing Power Index","Safety Index","Health Care Index",
"Property price to income ratio","Affordability Index","Cost Of Living Index",
"Cost Of Living Plus Rent Index","Life expectancy at birth, total (years)",
"Military expenditure (% of GDP)", "Tax revenue (% of GDP)" )

val newCol = Seq("country", "population", "gdp", "gdpPerCapita","unemployment", 
"populationGrowthRate", "urbanPop","urbanPopGrowth", "healthTotal", "educationTotal",
 "internetUsers", "qualityOfLifeI", "PPI", "safetyI", "HealthI", "propPriceToIncome",
 "affordabilityI", "costI", "costPlusRentI", "lifeExpectancy", "militaryTotal", "taxes")
//bytt alle kolonnenavn
val list = oldCol.zip(newCol).map(f=>{col(f._1).as(f._2)})
//hent ut den nye df-en
val newDF = myDf.select(list:_*)
//skriv til CSV
newDF.write.format("csv").mode("overwrite").option("header", true)
.save("country-profiles.csv")
