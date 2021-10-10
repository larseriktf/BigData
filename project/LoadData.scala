import java.io._
import scala.collection.mutable.ArrayBuffer
import org.apache.commons._
import java.util.ArrayList
import org.apache.http.{ io => _, _ }
import org.apache.http.client._
import org.apache.http.entity.StringEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import com.google.gson.Gson

object AverageGradesByStudyHours extends App {

    def calculateAverage(array:ArrayBuffer[Int]) : Float = {
        var average = 0f;
        var i = 0;
        array.foreach(x => {
            average += x;
            i += 1;
        });
        return average / i;
    }

    val source = io.Source.fromFile("datasets/student-performance.csv");
    var lines = source.getLines().map(line => line.split(",")).drop(1);

    // Temp ArrayBuffers
    // Female grades
    var gradesFLessThanTwo = ArrayBuffer[Int]();
    var gradesFTwoToFive = ArrayBuffer[Int]();
    var gradesFFiveToTen = ArrayBuffer[Int]();
    var gradesFGreaterThanTen = ArrayBuffer[Int]();

    // Male grades
    var gradesMLessThanTwo = ArrayBuffer[Int]();
    var gradesMTwoToFive = ArrayBuffer[Int]();
    var gradesMFiveToTen = ArrayBuffer[Int]();
    var gradesMGreaterThanTen = ArrayBuffer[Int]();
    
    // Fill ArrayBuffers with grades
    lines.foreach(line => {
        if (line(1) == "\"F\"") {
            if (line(13) == "1") gradesFLessThanTwo += line(32).toInt;
            if (line(13) == "2") gradesFTwoToFive += line(32).toInt;
            if (line(13) == "3") gradesFFiveToTen += line(32).toInt;
            if (line(13) == "4") gradesFGreaterThanTen += line(32).toInt;
        }
        if (line(1) == "\"M\"") {
            if (line(13) == "1") gradesMLessThanTwo += line(32).toInt;
            if (line(13) == "2") gradesMTwoToFive += line(32).toInt;
            if (line(13) == "3") gradesMFiveToTen += line(32).toInt;
            if (line(13) == "4") gradesMGreaterThanTen += line(32).toInt;
        }
    });

    case class AvgGrades(female: Float, male: Float);
    case class AvgGradesByStudyHours(
        lessThanTwo: AvgGrades,
        twoToFive: AvgGrades,
        fiveToTen: AvgGrades,
        greaterThanTen: AvgGrades);

    var avgLessThanTwo = AvgGrades(
        calculateAverage(gradesFLessThanTwo),
        calculateAverage(gradesMLessThanTwo)
    );
    var avgTwoToFive = AvgGrades(
        calculateAverage(gradesFTwoToFive),
        calculateAverage(gradesMTwoToFive)
    );
    var avgFiveToTen = AvgGrades(
        calculateAverage(gradesFFiveToTen),
        calculateAverage(gradesMFiveToTen)
    );
    var avgGreaterThanTen = AvgGrades(
        calculateAverage(gradesFGreaterThanTen),
        calculateAverage(gradesMGreaterThanTen)
    );
    var avgGradesByStudyHours = AvgGradesByStudyHours(
        avgLessThanTwo,
        avgTwoToFive,
        avgFiveToTen,
        avgGreaterThanTen
    );
    val avgGradesAsJson = new Gson().toJson(avgGradesByStudyHours);

    val post = new HttpPost("http://127.0.0.1:2379/v2/keys/component%3AaverageGradesByStudyHours");

    
    val nameValuePairs = new ArrayList[NameValuePair]();
    nameValuePairs.add(new BasicNameValuePair("value", avgGradesAsJson));
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    

    /*
    // set the Content-type
    post.setHeader("Content-type", "application/json")

    //val nameValuePair = new BasicNameValuePair("value", jsonObject));
    post.setEntity(new StringEntity(avgGradesAsJson));

    */

    // send the post request
    val client = new DefaultHttpClient
    val response = client.execute(post)
    println("--- HEADERS ---")
    response.getAllHeaders.foreach(arg => println(arg))
}