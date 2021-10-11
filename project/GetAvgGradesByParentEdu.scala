import java.io._
import org.apache.commons._
import org.apache.http._
import org.apache.http.client._
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import java.util.ArrayList
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import com.google.gson.Gson


case class NodeResponse(key : String, value: String)
case class Node(key : String, value: String, nodes: Array[NodeResponse])
case class Message(action: String, node: Node)

case class AvgGrades(
    motherEducation: Float,
    fatherEducation: Float);
case class AvgGradesByParentEdu(
    none: AvgGrades,
    primaryEducation: AvgGrades,
    fifthToNinthGrade: AvgGrades,
    secondaryEducation: AvgGrades,
    higherEducation: AvgGrades);

object HttpGetPostTest extends App {

    val url = "http://127.0.0.1:2379/v2/keys/component%3AaverageGradesByParentEducation";
    val result = scala.io.Source.fromURL(url).mkString;
    
    println(result);
    
    val messageParsed = new Gson().fromJson( result, classOf[Message] );
    val valueParsed = new Gson().fromJson( messageParsed.node.nodes(0).value, classOf[AvgGradesByParentEdu] );
    
    println("Average Grades By Study Time:");
    println("none:");
    println("    mother edu: " + valueParsed.none.motherEducation);
    println("    father edu: " + valueParsed.none.fatherEducation);
    println("primary:");
    println("    mother edu: " + valueParsed.primaryEducation.motherEducation);
    println("    father edu: " + valueParsed.primaryEducation.fatherEducation);
    println("fiveToNinth:");
    println("    mother edu: " + valueParsed.fifthToNinthGrade.motherEducation);
    println("    father edu: " + valueParsed.fifthToNinthGrade.fatherEducation);
    println("secondary:");
    println("    mother edu: " + valueParsed.secondaryEducation.motherEducation);
    println("    father edu: " + valueParsed.secondaryEducation.fatherEducation);
    println("higher:");
    println("    mother edu: " + valueParsed.higherEducation.motherEducation);
    println("    father edu: " + valueParsed.higherEducation.fatherEducation);
}