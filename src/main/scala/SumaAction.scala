import Components.Controller

import java.util.function.{Function => JFunction}

object SumaAction extends App {

  val controller = new Controller[Map[String, Int], Int]()

  val sleepFunction: JFunction[Map[String, Int], Int] = new JFunction[Map[String, Int], Int] {
    override def apply(args: Map[String, Int]): Int = {
      val x = args.getOrElse("x", 0)
      val y = args.getOrElse("y", 0)
      x + y
    }
  }

  controller.registerAction("addAction", sleepFunction, 150)

  val res: AnyRef = controller.invoke("addAction", Map("x" -> 10, "y" -> 5))

  println("Function result: " + sleepFunction + " is: " + res)
}