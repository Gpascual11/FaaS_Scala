package Decorator

import Components.Controller

import java.util.function.{Function => JFunction}

object TestMemoizationDecorator extends App {

  val controller = new Controller[Map[String, Int], Int]()

  val sleepFunction: JFunction[Map[String, Int], Int] = (args: Map[String, Int]) => {
    var x = args.getOrElse("x", 0)
    for (i <- 1 to 1000000000) {
      x = 1 + i
    }
    val y = args.getOrElse("y", 0)
    x + y
  }

  // Define a Scala Function
  private val memoizationDecorator = new MemoizationDecoratorScala[Map[String, Int], Int](controller.getInvoker(0).getCache, sleepFunction)

  // Register the Java Function with the controller
  controller.registerAction2("printAny", memoizationDecorator, 150)

  // Execute the function
  controller.invoke("printAny", Map("x" -> 10, "y" -> 5))
  controller.invoke("printAny", Map("x" -> 10, "y" -> 5))
  controller.invoke("printAny", Map("x" -> 10, "y" -> 7))
  controller.invoke("printAny", Map("x" -> 10, "y" -> 5))
  controller.invoke("printAny", Map("x" -> 10, "y" -> 7))
}
