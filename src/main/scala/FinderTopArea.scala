import ReadUrl.urlInto
import io.circe._
import io.circe.parser.{decode, _}
import io.circe.generic.extras._
import io.circe.generic.auto._
import cats.implicits._
import io.circe.syntax._

import scala.io.{BufferedSource, Source}
import java.net.URL
import scala.util.chaining._
import java.io.{FileOutputStream, IOException, PrintStream}

object FinderTopArea extends App {
  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames

  @ConfiguredJsonCodec case class GitData(region: String, name: Name, capital: Array[String], area: Float)

  @ConfiguredJsonCodec final case class Name(official: String)

  case class ResponseData(name: String, capital: String, area: Float)

  val url: String = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json"

  /* val dd = Either.catchNonFatal(Source.fromURL(url))
      .map(_.getLines.mkString)
      .flatMap(decode[List[GitData]])*/

  def getData: Either[Throwable, List[GitData]] = {
    url.pipe(urlInto[List[GitData]])
  }

  def findAfricaSorted: List[ResponseData] = {
    val sortData: List[ResponseData] = getData match {
      case Right(value) => value.map {
        case el@GitData(region, _, _, _) if region == "Africa" => ResponseData(el.name.official, el.capital(0), el.area)
        case GitData(_, _, _, _) => ResponseData("", "", 0)
      }.filter(el => el.name != "")
      case Left(error) => List(ResponseData("", "", 0))
    }
    sortData.sortBy(el => el.area).reverse
  }

 // val sortFF = findAfrica.sortBy(el => el.area).reverse
  val outputFile = args(0)
  val fos = new FileOutputStream(outputFile)

  try {
    val personEncodeJsonToString = findAfricaSorted.take(10).asJson.spaces2
    val printStream = new PrintStream(fos)
    printStream.println(personEncodeJsonToString)
  } catch {
    case e: IOException => println(e.getMessage)
  }
  println(">>> Done <<<")

}
