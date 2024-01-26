package Decorator

import Components.Controller

import java.util.function.{ Function => JFunction }
import scala.jdk.FunctionConverters._
import scala.jdk.javaapi.FunctionConverters.asJavaFunction

object TestMemoizationDecorator extends App {

  val controller = new Controller[Int, String](1)
  val cache = scala.collection.mutable.Map[String, Any]()

  // Using FunctionConverter to create a JFunction
  val sleepF: JFunction[Map[String, Int], Int] =
    asJavaFunction((args: Map[String, Int]) => {
      val x = args.getOrElse("x", 0)
      val y = args.getOrElse("y", 0)
      x + y
    })

  // Register the sleep function with memoization and a specific amount of RAM
  val sleep1: MemoizationDecorator[Map[String, Int], Any] =
    new MemoizationDecorator(controller.getInvoker(0).getCache(), sleepF)

  // Define a Scala Function
  val sleep1Function: Function[Int, String] = (value: Int) => sleep1.apply(Map("x" -> value)).toString

  // Convert the Scala Function to a Java Function
  val sleep1JavaFunction: JFunction[Int, String] = asJavaFunction(sleep1Function)

  // Register the Java Function with the controller
  controller.registerAction("sleep1", sleep1JavaFunction)

  // Execute the function
  controller.invoke("printAny", 3)
  controller.invoke("printAny", 5)
  controller.invoke("printAny", 5)
  controller.invoke("printAny", 3)
}
