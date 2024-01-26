package MapReduce

object WordCountScala {

  def wordCountMap(text: String): Map[String, Int] = {
    text
      .split("\\s+")
      .map(word => word.toLowerCase().replaceAll("[^a-zA-Z]", ""))
      .foldLeft(Map.empty[String, Int]) { (acc, word) =>
        acc + (word -> (acc.getOrElse(word, 0) + 1))
      }
  }
}