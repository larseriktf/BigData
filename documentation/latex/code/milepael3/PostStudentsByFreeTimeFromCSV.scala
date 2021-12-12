case class StudentsByFreeTime(
    veryLittle: Float,
    little: Float,
    medium: Float,
    much: Float,
    veryMuch: Float);

object StudentsByFreeTime extends App {

    val source = io.Source.fromFile("datasets/student-performance.csv");
    var lines = source.getLines().map(line => line.split(",")).drop(1);
    
    var veryLittle = 0f;
    var little = 0f;
    var medium = 0f;
    var much = 0f;
    var veryMuch = 0f;

    lines.foreach(line => {
        val freeTime = line(24).toInt;

        if (freeTime == 1) veryLittle += 1;
        if (freeTime == 2) little += 1;
        if (freeTime == 3) medium += 1;
        if (freeTime == 4) much += 1;
        if (freeTime == 5) veryMuch += 1;
    });

    val total = veryLittle + little + medium + much + veryMuch;

    veryLittle = (veryLittle / total) * 100;
    little = (little / total) * 100;
    medium = (medium / total) * 100;
    much = (much / total) * 100;
    veryMuch = (veryMuch / total) * 100;

    val studentsByFreeTime = StudentsByFreeTime(
        veryLittle,
        little,
        medium,
        much,
        veryMuch
    );

    val spock = new Gson().toJson(studentsByFreeTime);

    val post = new HttpPost("http://127.0.0.1:2379/v2/keys/component%3AstudentsByFreeTime");

    val nameValuePairs = new ArrayList[NameValuePair]();
    nameValuePairs.add(new BasicNameValuePair("value", spock));
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    // send the post request
    val client = new DefaultHttpClient;
    val response = client.execute(post);
    println("--- HEADERS ---");
    response.getAllHeaders.foreach(arg => println(arg));
}
