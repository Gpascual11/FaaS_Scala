package MapReduce

object TestMapReduceScala {
  def reduce(results: Array[scala.collection.immutable.Map[String, Int]], targetWord: String): Int = {
    results.map(_.getOrElse(targetWord, 0)).sum
  }

  def main(args: Array[String]): Unit = {
    val filePaths = Array(
      "src/main/java/Files/pg100.txt",
      "src/main/java/Files/pg145.txt",
      "src/main/java/Files/pg394.txt",
      "src/main/java/Files/pg1342.txt",
      "src/main/java/Files/pg1513.txt",
      "src/main/java/Files/pg2160.txt",
      "src/main/java/Files/pg2641.txt"
    )

    for (filePath <- filePaths) {
      val textFromFile = MapReduceUtilScala.readTextFromFile(filePath)

      val wordCountResult = WordCountScala.wordCountMap(textFromFile)

      val countWordsResult = CountWordsScala.countWordsMap(textFromFile)
      println(s"CountWords para $filePath: $countWordsResult")

      val targetWord = "how"
      val totalWordCount = TestMapReduceScala.reduce(
        Array(wordCountResult, countWordsResult),
        targetWord
      )
      println(s"Total of the word: '$targetWord' for:  $filePath: $totalWordCount")
    }
  }
}