package MapReduce

import scala.io.Source
import scala.util.{Try, Failure, Success}

object MapReduceUtilScala {

  def readTextFromFile(filePath: String): String = {
    val result: Try[String] = Try {
      val source = Source.fromFile(filePath)
      try {
        source.mkString
      } finally {
        source.close()
      }
    }

    result match {
      case Success(text) => text
      case Failure(exception) =>
        exception.printStackTrace()
        ""
    }
  }
}
