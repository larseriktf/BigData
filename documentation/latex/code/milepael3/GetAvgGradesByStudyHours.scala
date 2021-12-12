case class NodeResponse(key : String, value: String)
case class Node(key : String, value: String, nodes: Array[NodeResponse])
case class Message(action: String, node: Node)

case class AvgGrades(female: Float, male: Float);
case class AvgGradesByStudyHours(
    lessThanTwo: AvgGrades,
    twoToFive: AvgGrades,
    fiveToTen: AvgGrades,
    greaterThanTen: AvgGrades);

object HttpGetPostTest extends App {

    val url = "http://127.0.0.1:2379/v2/keys/component%3AaverageGradesByStudyHours";
    val result = scala.io.Source.fromURL(url).mkString;
    
    println(result);
    
    val messageParsed = new Gson().fromJson( result, classOf[Message] );
    val valueParsed = new Gson().fromJson( messageParsed.node.nodes(0).value, classOf[AvgGradesByStudyHours] );
    
    println("Average Grades By Study Time:");
    println("< 2:");
    println("    female: " + valueParsed.lessThanTwo.female);
    println("    male: " + valueParsed.lessThanTwo.male);
    println("2 - 5:");
    println("    female: " + valueParsed.twoToFive.female);
    println("    male: " + valueParsed.twoToFive.male);
    println("5 - 10:");
    println("    female: " + valueParsed.fiveToTen.female);
    println("    male: " + valueParsed.fiveToTen.male);
    println("> 10:");
    println("    female: " + valueParsed.greaterThanTen.female);
    println("    male: " + valueParsed.greaterThanTen.male);
}
