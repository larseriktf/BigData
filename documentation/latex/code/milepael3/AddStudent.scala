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

object AddStudent extends App {

    println("--- Fill In The Following Fields ---");
    print("sex (Char: F or M): ");
    val sex = readChar();
    print("Mother's Education (Int: 0-4): ");
    val motherEdu = readInt();
    print("Father's Education (Int: 0-4): ");
    val fatherEdu = readInt();
    print("Study Time (Int: 1-4): ");
    val studyTime = readInt();
    print("School Support (Boolean: true/false): ");
    val schoolSupport = readBoolean();
    print("Extra Paid Courses (Boolean: true/false): ");
    val extraPaidCourses = readBoolean();
    print("Internet (Boolean true/false): ");
    val internet = readBoolean();
    print("Family Relation (Int: 1-5): ");
    val familyRelation = readInt();
    print("Free Time (Int: 1-5): ");
    val freeTime = readInt();
    print("Weekday Alcohol (Int: 1-5): ");
    val weekdayAlcohol = readInt();
    print("Weekend Alcohol (Int: 1-5): ");
    val weekendAlcohol = readInt();
    print("Final Grade (Int: 1 - 20): ");
    val finalGrade = readInt();

    val student = Student(
        sex.toUpper,
        motherEdu,
        fatherEdu,
        studyTime,
        schoolSupport,
        extraPaidCourses,
        internet,
        familyRelation,
        freeTime,
        weekdayAlcohol,
        weekendAlcohol,
        finalGrade
    );
    
    println(student);

    val post = new HttpPost("http://127.0.0.1:2379/v2/keys/student");
    
    val spock = new Gson().toJson(student);
    val nameValuePairs = new ArrayList[NameValuePair]();
    nameValuePairs.add(new BasicNameValuePair("value", spock));
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    // send the post request
    val client = new DefaultHttpClient;
    val response = client.execute(post);
    println("--- HEADERS ---");
    response.getAllHeaders.foreach(arg => println(arg));

    println("--- Data Posted! ---");
}
