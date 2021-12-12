case class AvgGrades(
    motherEducation: Float,
    fatherEducation: Float);
case class AvgGradesByParentEdu(
    none: AvgGrades,
    primaryEducation: AvgGrades,
    fifthToNinthGrade: AvgGrades,
    secondaryEducation: AvgGrades,
    higherEducation: AvgGrades);

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
    val gradesMotherNone = ArrayBuffer[Int]();
    val gradesMotherPrimary = ArrayBuffer[Int]();
    val gradesMotherFifthToNinth = ArrayBuffer[Int]();
    val gradesMotherSecondary = ArrayBuffer[Int]();
    val gradesMotherHigher = ArrayBuffer[Int]();

    // Male grades
    val gradesFatherNone = ArrayBuffer[Int]();
    val gradesFatherPrimary = ArrayBuffer[Int]();
    val gradesFatherFifthToNinth = ArrayBuffer[Int]();
    val gradesFatherSecondary = ArrayBuffer[Int]();
    val gradesFatherHigher = ArrayBuffer[Int]();
    
    // Fill ArrayBuffers with grades
    lines.foreach(line => {
        val mEdu = line(6).toInt;
        val fEdu = line(7).toInt;
        val finalGrade = line(32).toInt;

        if (mEdu == 0) gradesMotherNone += finalGrade;
        if (mEdu == 1) gradesMotherPrimary += finalGrade;
        if (mEdu == 2) gradesMotherFifthToNinth += finalGrade;
        if (mEdu == 3) gradesMotherSecondary += finalGrade;
        if (mEdu == 4) gradesMotherHigher += finalGrade;
        
        if (fEdu == 0) gradesFatherNone += finalGrade;
        if (fEdu == 1) gradesFatherPrimary += finalGrade;
        if (fEdu == 2) gradesFatherFifthToNinth += finalGrade;
        if (fEdu == 3) gradesFatherSecondary += finalGrade;
        if (fEdu == 4) gradesFatherHigher += finalGrade;
    });

    val avgGradesByParentEdu = AvgGradesByParentEdu(
        AvgGrades(
            calculateAverage(gradesMotherNone),
            calculateAverage(gradesFatherNone)
        ),
        AvgGrades(
            calculateAverage(gradesMotherPrimary),
            calculateAverage(gradesFatherPrimary)
        ),
        AvgGrades(
            calculateAverage(gradesMotherFifthToNinth),
            calculateAverage(gradesFatherFifthToNinth)
        ),
        AvgGrades(
            calculateAverage(gradesMotherSecondary),
            calculateAverage(gradesFatherSecondary)
        ),
        AvgGrades(
            calculateAverage(gradesMotherHigher),
            calculateAverage(gradesFatherHigher)
        )
    );

    val spock = new Gson().toJson(avgGradesByParentEdu);

    val post = new HttpPost("http://127.0.0.1:2379/v2/keys/component%3AaverageGradesByParentEducation");

    val nameValuePairs = new ArrayList[NameValuePair]();
    nameValuePairs.add(new BasicNameValuePair("value", spock));
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    // send the post request
    val client = new DefaultHttpClient
    val response = client.execute(post)
    println("--- HEADERS ---")
    response.getAllHeaders.foreach(arg => println(arg))
}
