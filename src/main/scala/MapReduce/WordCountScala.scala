package MapReduce

/**
 * WordCountScala object
 */
object WordCountScala {

  /**
   * wordCount method
   * Counts the number of words in a text
   *
   * @param text the text to count the words
   * @return the number of words in the text
   */
  def wordCountMap(text: String): Map[String, Int] = {
    text
      .split("\\s+")
      .map(word => word.toLowerCase().replaceAll("[^a-zA-Z]", ""))
      .foldLeft(Map.empty[String, Int]) { (acc, word) =>
        acc + (word -> (acc.getOrElse(word, 0) + 1))
      }
  }
}