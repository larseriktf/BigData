//fikse datasett
val regime = PDF.select($"cowcode".as("cowcode"), 
$"gwf_country".as("country"), 
$"gwf_casename".as("casename"), 
$"gwf_startdate".as("startdate"), 
$"gwf_enddate".as("enddate"), 
$"gwf_regimetype".as("regimetype"))

val reignDf = PDF.select($"ccode".as("ccode"), 
    $"country".as("country"), 
    $"leader".as("leader"), 
    $"year".as("year"), 
    $"month".as("month"), 
    $"elected".as("elected"), 
    $"age".as("age"),
    $"militarycareer".as("militaryCareer"), 
    $"government".as("government"), 
    $"gov_democracy".as("govDemocracy"), 
    $"political_violence".as("politicalViolence"), 
    $"couprisk".as("coupRisk"))

//skrive til neo4j
regime.write.format("org.neo4j.spark.DataSource")
    .mode("Overwrite")
    .option("url", "bolt://localhost:7687")
    .option("authentication.basic.username", "neo4j")
    .option("authentication.basic.password", "student")
    .option("labels", ":Regime ")
    .option("node.keys", "casename")
    .save()



reignDf.write.format("org.neo4j.spark.DataSource")
    .mode("Overwrite")
    .option("url", "bolt://localhost:7687")
    .option("authentication.basic.username", "neo4j")
    .option("authentication.basic.password", "student")
    .option("labels", ":Reign")
    .option("node.keys", "ccode").save()


must use `` when (.)dot is used in names
df.select("`source.country`").show()

//legge til relasjon/joining av datasett gjort i Cypher
MATCH (regime:Regime)
MERGE (reign:Reign {gov: regime.regimetype})
RETURN reign, regime

reignDf.write.format("org.neo4j.spark.DataSource")
    .mode("Overwrite")
    .option("url", "bolt://localhost:7687")
    .option("authentication.basic.username", "neo4j")
    .option("authentication.basic.password", "student")
    .option("node.keys", "ccode").save()


//Laste med relasjoner
val df = spark.read.format("org.neo4j.spark.DataSource")
    .option("url", "bolt://localhost:7687")
    .option("authentication.basic.username", "neo4j")
    .option("authentication.basic.password", "student")
    .option("relationship", "HAD_REGIME")
    .option("relationship.source.labels", "Reign")
    .option("relationship.source.keys", "ccode")
    .option("relationship.target.labels", "Regime")
    .option("relationship.target.keys", "cowcode")
    .load()
