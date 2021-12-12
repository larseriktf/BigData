case class Student(
    sex: Char,
    motherEdu: Int,
    fatherEdu: Int,
    studyTime: Int,
    schoolSupport: Boolean,
    extraPaidCourses: Boolean,
    internet: Boolean,
    familyRelation: Int,
    freeTime: Int,
    weekdayAlcohol: Int,
    weekendAlcohol: Int,
    finalGrade: Int
);

object Student extends App {
    val source = io.Source.fromFile("datasets/student-performance.csv");
    val lines = source.getLines().map(line => line.split(",")).drop(1);

    val students = ArrayBuffer[Student](); 

    // Read and add students
    lines.foreach(line => {
        val student = Student(
            line(1)(1),
            line(6).toInt,
            line(7).toInt,
            line(13).toInt,
            if (line(15) == "\"yes\"") true else false,
            if (line(17) == "\"yes\"") true else false,
            if (line(21) == "\"yes\"") true else false,
            line(23).toInt,
            line(24).toInt,
            line(26).toInt,
            line(27).toInt,
            line(32).toInt
        );

        students += student;
    });

    val post = new HttpPost("http://127.0.0.1:2379/v2/keys/student");
    
    students.foreach(student => {
        val spock = new Gson().toJson(student);
        val nameValuePairs = new ArrayList[NameValuePair]();
        nameValuePairs.add(new BasicNameValuePair("value", spock));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    
        // send the post request
        val client = new DefaultHttpClient;
        val response = client.execute(post);
        println("--- HEADERS ---");
        response.getAllHeaders.foreach(arg => println(arg));
    });
}