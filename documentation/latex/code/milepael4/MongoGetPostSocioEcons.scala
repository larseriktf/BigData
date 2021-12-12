val source = io.Source.fromFile("datasets/soci_econ_country_profiles.csv");
    
source.getLines.drop(1).foreach { line =>
    val row = line.split(",").map(_.trim)

    def parseFloat(value : String) : Option[Float] = if (value == "") None else 
    Some(value.toFloat)

    def parseInt(value : String) : Option[Int] = if (value == "") None else
    Some(value.toInt)
    
    def isEmpty( input : Option[String]) : Boolean = 
        input match {
        case None    => true
        case Some(s) => s.trim.isEmpty
    }

    //parses incorrect values to a nothing or something value
    def isWrongData(value : String) : Option[Double] = if (value == "~0.0" || value == "-~0.0" || value == "...") None else
    Some(value.toDouble)
    
    val document = Document(
        "country" -> row(1),
        "populationInThousands" -> row(4).toDouble,
        "gdp" -> row(7).toDouble,
        "gdpPerCapita" -> row(9).toDouble,
        "unemployment" -> row(16).toDouble,
        "labourForceGender" -> row(17),
        "popGrowthRateAnnual" -> isWrongData(row(24)),
        "urbanPop" -> isWrongData(row(25)),
        "urbanPopGrowth" -> isWrongData(row(26)),
        "healthTotalExpenditure" -> row(32).toDouble,
        "educationTotalExpenditure" -> isWrongData(row(34)),
        "internetUsersPerOneHundred" -> row(41).toDouble,
        "qualityOfLifeIndex" -> row(50).toDouble,
        "purchasingPowerIndex" -> row(51).toDouble,
        "safetyIndex" -> row(52).toDouble,
        "healthCareIndex" -> row(53).toDouble,
        "propertyPriceIncomeRatio" -> row(55).toDouble,
        "affordabilityIndex" -> row(64).toDouble,
        "costOfLivingIndex" -> row(65).toDouble,
        "costOfLivingPlusRentIndex" -> row(67).toDouble,
        "lifeExpectancy" -> row(89).toDouble,
        "militaryExpenditure" -> {if (!row(90).isEmpty) row(90).toDouble else 0},
        "taxRevenue" -> {if (!row(93).isEmpty) row(93).toDouble else 0}
    ); 
    collection.insertOne(document).results();
}

println("All documents inserted")
