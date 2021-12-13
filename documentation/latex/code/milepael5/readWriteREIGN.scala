// Read from csv
val leaderDf = spark.read.format("csv").option("header", "true").option("inferSChema", "true").load("datasets/REIGN_dataset/leader_list_6_21.csv")
val reignDf = spark.read.format("csv").option("header", "true").option("inferSChema", "true").load("datasets/REIGN_dataset/REIGN_2021_6.csv")
val regimeDf = spark.read.format("csv").option("header", "true").option("inferSChema", "true").load("datasets/REIGN_dataset/regime_list.csv")
val electionDf = spark.read.format("csv").option("header", "true").option("inferSChema", "true").load("datasets/REIGN_dataset/election_6_21.csv")

// Write to parquet
repartition(1).write.format("parquet").mode("overwrite")
    .save("parquets/leader_write.parquet")
repartition(1).write.format("parquet").mode("overwrite")
    .save("parquets/reign_write.parquet")
repartition(1).write.format("parquet").mode("overwrite")
    .save("parquets/regime_write.parquet")
repartition(1).write.format("parquet").mode("overwrite")
    .save("parquets/election_write.parquet")