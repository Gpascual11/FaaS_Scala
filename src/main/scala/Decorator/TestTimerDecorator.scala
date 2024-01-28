package Decorator

import Components.Controller
import java.util.function.{Function => JFunction}

object TestTimerDecorator extends App {

  val controller = new Controller[Map[String, Int], Int]()

  val sleepFunction: JFunction[Map[String, Int], Int] = (args: Map[String, Int]) => {
    var x = args.getOrElse("x", 0)
    for (i <- 1 to 1000000000) {
      x = 1 + i
    }
    val y = args.getOrElse("y", 0)
    x + y
  }

  private val timerDecorator = new TimerDecoratorScala[Map[String, Int], Int](sleepFunction)
  controller.registerAction2("addAction", timerDecorator, 150)

  val res: AnyRef = controller.invoke("addAction", Map("x" -> 10, "y" -> 5))
  println(res)
}
