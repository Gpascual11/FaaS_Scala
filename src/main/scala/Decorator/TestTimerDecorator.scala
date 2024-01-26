package Decorator

import Components.Controller
import java.util.function.{Function => JFunction}

object TestTimerDecorator extends App {

  val controller = new Controller[Map[String, Int], Int]()
  val cache = scala.collection.mutable.Map[String, Int]()
  val timerDecorator = new TimerDecorator[Map[String, Int], Int]

  val sleepFunction: JFunction[Map[String, Int], Int] = new JFunction[Map[String, Int], Int] {
    override def apply(args: Map[String, Int]): Int = {

      var x = args.getOrElse("x", 0)
      for (i <- 1 to 1000000000) {
        x = 1 + i
      }
      val y = args.getOrElse("y", 0)
      x + y
    }
  }

  controller.registerAction("addAction", sleepFunction, 150)

  val res: AnyRef = controller.invoke("addAction", Map("x" -> 10, "y" -> 5))

  timerDecorator.apply(sleepFunction, Map("x" -> 10, "y" -> 5))
  println("El resultat de " + sleepFunction + " és " + res)
  timerDecorator.apply(sleepFunction, Map("x" -> 10, "y" -> 5))
  println("El resultat de " + sleepFunction + " és " + res)
  timerDecorator.apply(sleepFunction, Map("x" -> 200, "y" -> 500))
  println("El resultat de " + sleepFunction + " és " + res)
  timerDecorator.apply(sleepFunction, Map("x" -> 10, "y" -> 5))
  println("El resultat de " + sleepFunction + " és " + res)
  timerDecorator.apply(sleepFunction, Map("x" -> 200, "y" -> 500))
  println("El resultat de " + sleepFunction + " és " + res)
  timerDecorator.apply(sleepFunction, Map("x" -> 1050, "y" -> 5))
  println("El resultat de " + sleepFunction + " és " + res)
  timerDecorator.apply(sleepFunction, Map("x" -> 1050, "y" -> 5))
  println("El resultat de " + sleepFunction + " és " + res)

}
