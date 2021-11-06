import org.mongodb.scala._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model.UpdateOptions
import org.mongodb.scala.model._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.collection.JavaConverters._
import org.mongodb.scala.bson.BsonObjectId
import java.io._


import java.util.List
import java.util.Arrays
import java.util.ArrayList
import java.util.concurrent.TimeUnit
/*
case class CountryProfile(
    country: String
    populationInThousands: Double
    gdp: Double // line7
    gdpPerCapita: Double //line9
    unemployment: Double //percentage of population / line16
    labourForceGender: Double //percentage labour force participation by gender female%/male%
    //gender- line17
    popGrowthRateAnnual: Double //percentage line24
    urbanPop: Double //percentage line25
    urbanPopGrowth: Double //percent avg annual line26
    healthTotalExpenditure: Double //percent of GDP line32
    educationTotalExpenditure: Double //percent of GDP line 34
    internetUsersPerOneHundred: Int //line 41
    qualityOfLifeIndex: Double //line50
    purchasingPowerIndex: Double //line51
    safetyIndex: Double //line52
    healthCareIndex: Double //line53
    
    propertyPriceIncomeRatio: Double //line55
    affordabilityIndex: Double //line64
    costOfLivingIndex: Double //line65
    costOfLivingPlusRentIndex: Double //line67
    lifeExpectancy: Double //line89
    militaryExpenditure: Double //line90
    taxRevenue: Double //% of GDP line93
)
*/
object PostANewCountryProfile extends App {
    implicit class DocumentObservable[C](val observable: Observable[Document]) extends ImplicitObservable[Document] {
        override val converter: (Document) => String = (doc) => doc.toJson
    }

    implicit class GenericObservable[C](val observable: Observable[C]) extends ImplicitObservable[C] {
        override val converter: (C) => String = (doc) => doc.toString
    }

    

    trait ImplicitObservable[C] {
        val observable: Observable[C]

        val converter: (C) => String
        def results(): Seq[C] = Await.result(observable.toFuture(), Duration(10, TimeUnit.SECONDS))
        def headResult() = Await.result(observable.head(), Duration(10, TimeUnit.SECONDS))
        def printResults(initial: String = ""): Unit = {
            if (initial.length > 0) print(initial)
            results.foreach(res => println(converter(res)))
        }
        def printHeadResult(initial: String = ""): Unit = println(s"${initial}${converter(headResult())}")
    }

    val mongoClient: MongoClient = MongoClient();
    val database: MongoDatabase = mongoClient.getDatabase("soci_econ_country_profiles");
    val collection: MongoCollection[Document] = database.getCollection("countryProfiles");

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

}