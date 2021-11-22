val regime = PDF.select($"cowcode".as("source.cowcode"), 
$"gwf_country".as("source.country"), 
$"gwf_casename".as("source.casename"), 
$"gwf_startdate".as("source.startdate"), 
$"gwf_enddate".as("source.enddate"), 
$"gwf_regimetype".as("source.regimetype"))

regime.write.format("org.neo4j.spark.DataSource")
    .mode("Overwrite")
    .option("url", "bolt://localhost:7687")
    .option("authentication.basic.username", "neo4j")
    .option("authentication.basic.password", "student")
    .option("labels", ":Regime ")
    .option("node.keys", "source.casename")
    .save()

val reignDf = PDF.select($"ccode".as("source.ccode"), 
    $"country".as("source.country"), 
    $"leader".as("source.leader"), 
    $"year".as("source.year"), 
    $"month".as("source.month"), 
    $"elected".as("source.elected"), 
    $"age".as("source.age"),
    $"militarycareer".as("source.militaryCareer"), 
    $"government".as("source.government"), 
    $"gov_democracy".as("source.govDemocracy"), 
    $"political_violence".as("source.politicalViolence"), 
    $"couprisk".as("source.coupRisk"))

reignDf.write.format("org.neo4j.spark.DataSource")
    .mode("Overwrite")
    .option("url", "bolt://localhost:7687")
    .option("authentication.basic.username", "neo4j")
    .option("authentication.basic.password", "student")
    .option("labels", ":Reign")
    .option("node.keys", "source.ccode").save()


must use `` when (.dot) is used in names
df.select("`source.source.country`").show()

//legge til relasjon/joining av datasett
MATCH (reign:Reign)
MERGE (regime:Regime {country: reign.country})
RETURN reign, regime

//Laste med relasjoner
val df = spark.read.format("org.neo4j.spark.DataSource")
    .option("url", "bolt://localhost:7687")
    .option("authentication.basic.username", "neo4j")
    .option("authentication.basic.password", "student")
    .option("relationship", "HAD_REGIME")
    .option("relationship.source.labels", "Reign")
    .option("relationship.target.labels", "Regime")
    .load()
