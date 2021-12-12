case class AvgGrades(
    female: Float,
    male: Float);
case class AvgGradesByStudyHours(
    lessThanTwo: AvgGrades,
    twoToFive: AvgGrades,
    fiveToTen: AvgGrades,
    greaterThanTen: AvgGrades);

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

    val avgLessThanTwo = AvgGrades(
        calculateAverage(gradesFLessThanTwo),
        calculateAverage(gradesMLessThanTwo)
    );
    val avgTwoToFive = AvgGrades(
        calculateAverage(gradesFTwoToFive),
        calculateAverage(gradesMTwoToFive)
    );
    val avgFiveToTen = AvgGrades(
        calculateAverage(gradesFFiveToTen),
        calculateAverage(gradesMFiveToTen)
    );
    val avgGreaterThanTen = AvgGrades(
        calculateAverage(gradesFGreaterThanTen),
        calculateAverage(gradesMGreaterThanTen)
    );
    val avgGradesByStudyHours = AvgGradesByStudyHours(
        avgLessThanTwo,
        avgTwoToFive,
        avgFiveToTen,
        avgGreaterThanTen
    );
    
    val spock = new Gson().toJson(avgGradesByStudyHours);

    val post = new HttpPost("http://127.0.0.1:2379/v2/keys/component%3AaverageGradesByStudyHours");

    val nameValuePairs = new ArrayList[NameValuePair]();
    nameValuePairs.add(new BasicNameValuePair("value", spock));
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    // send the post request
    val client = new DefaultHttpClient
    val response = client.execute(post)
    println("--- HEADERS ---")
    response.getAllHeaders.foreach(arg => println(arg))
}