println("--- Fill In The Following Fields ---");
print("Country (String: name): ");
val country = readLine();
print("populationInThousands (Int): ");
val populationInThousands = readDouble();
print("GDP (Double): ");
val gdp = readDouble();
print("GDP per capita (Double): ");
val gdpPerCapita = readDouble();
print("Unemployment (Double): ");
val unemployment = readDouble();
print("labourForceGender(String: f%/m%): ");
val labourForceGender = readLine();
print("popGrowthRateAnnual (Double): ");
val popGrowthRateAnnual = readDouble();
print("Urban pop (Double): ");
val urbanPop = readDouble();
print("Urban pop growth (Double): ");
val urbanPopGrowth = readDouble();
print("Total health expenditure (Double): ");
val healthTotalExpenditure = readDouble();
print("Total education expenditure (Double): ");
val educationTotalExpenditure = readDouble();
print("Internet users per 100 inhabitants(Double): ");
val internetUsersPerOneHundred = readDouble();
print("Quality of life index value (Double): ");
val qualityOfLifeIndex = readDouble();
print("Purchasing power index value (Double): ");
val purchasingPowerIndex = readDouble();
print("Safety index value (Double): ");
val safetyIndex = readDouble();
print("Health Care index value (Double): ");
val healthCareIndex = readDouble();
print("Property price to income ratio (Double): ");
val propertyPriceIncomeRatio = readDouble();
print("Affordability index value (Double): ");
val affordabilityIndex = readDouble();
print("Cost of living index value (Double): ");
val costOfLivingIndex = readDouble();
print("Cost of living + rent index value (Double): ");
val costOfLivingPlusRentIndex = readDouble();
print("Life expectancy (Double): ");
val lifeExpectancy = readDouble();
print("Total Military expendature (Double): ");
val militaryExpenditure = readDouble();
print("Tax revenue(% of GDP)(Double): ");
val taxRevenue = readDouble();

val document = Document(
    "country" -> country,
        "populationInThousands" -> populationInThousands,
        "gdp" -> gdp,
        "gdpPerCapita" -> gdpPerCapita,
        "unemployment" -> unemployment,
        "labourForceGender" -> labourForceGender,
        "popGrowthRateAnnual" -> popGrowthRateAnnual,
        "urbanPop" -> urbanPop,
        "urbanPopGrowth" -> urbanPopGrowth,
        "healthTotalExpenditure" -> healthTotalExpenditure,
        "educationTotalExpenditure" -> educationTotalExpenditure,
        "internetUsersPerOneHundred" -> internetUsersPerOneHundred,
        "qualityOfLifeIndex" -> qualityOfLifeIndex,
        "purchasingPowerIndex" -> purchasingPowerIndex,
        "safetyIndex" -> safetyIndex,
        "healthCareIndex" -> healthCareIndex,
        "propertyPriceIncomeRatio" -> propertyPriceIncomeRatio,
        "affordabilityIndex" -> affordabilityIndex,
        "costOfLivingIndex" -> costOfLivingIndex,
        "costOfLivingPlusRentIndex" -> costOfLivingPlusRentIndex,
        "lifeExpectancy" -> lifeExpectancy,
        "militaryExpenditure" -> militaryExpenditure,
        "taxRevenue" -> taxRevenue
);
    
collection.insertOne(document).results();