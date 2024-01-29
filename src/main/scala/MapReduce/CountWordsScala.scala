package MapReduce

/**
 * CountWordsScala object
 */
object CountWordsScala {

  /**
   * countWords method
   * Counts the number of words in a text
   *
   * @param text the text to count the words
   * @return the number of words in the text
   */
  def countWords(text: String): Int = {
    val words = text.split("\\s+") // Doble \\ para escapar el caracter \
    words.length
  }

  /**
   * countWordsMap method
   * Counts the number of words in a text and returns a map with the result
   *
   * @param text the text to count the words
   * @return a map with the result
   */
  def countWordsMap(text: String): Map[String, Int] = {
    Map("count" -> countWords(text))
  }
}
