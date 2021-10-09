import java.io._
import org.apache.commons._

/*
import org.apache.http._
import org.apache.http.client._
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import java.util.ArrayList
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import com.google.gson.Gson
*/

object LoadData extends App {

    val source = io.Source.fromFile("datasets/student-performance.csv");
    var lines = source.getLines();
    lines.foreach(line => println(line));


//    val post = new HttpPost("http://127.0.0.1:2379/v2/keys/component%3AaverageGradesByStudyHours");

/*
    val nameValuePairs = new ArrayList[NameValuePair]()
    nameValuePairs.add(new BasicNameValuePair("value", mariusAsJson))
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs))

    // send the post request
    val client = new DefaultHttpClient
    val response = client.execute(post)
    println("--- HEADERS ---")
    response.getAllHeaders.foreach(arg => println(arg))
*/
}