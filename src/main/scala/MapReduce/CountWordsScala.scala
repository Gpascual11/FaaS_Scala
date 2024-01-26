package MapReduce

object CountWordsScala {

  def countWords(text: String): Int = {
    val words = text.split("\\s+") // Doble \\ para escapar el caracter \
    words.length
  }

  def countWordsMap(text: String): Map[String, Int] = {
    Map("count" -> countWords(text))
  }
}
