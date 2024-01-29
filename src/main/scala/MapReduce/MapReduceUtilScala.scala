package MapReduce

import scala.io.Source
import scala.util.{Try, Failure, Success}

/**
 * CountWordsScala object
 */
object MapReduceUtilScala {

  /**
   * countWords method
   * Counts the number of words in a text
   *
   * @param filePath the path to the file to count the words
   * @return the number of words in the text
   */
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
