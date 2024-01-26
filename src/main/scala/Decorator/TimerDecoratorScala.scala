package Decorator


import Components.Action

import java.util.function.{Function => JFunction}

/**

Decorator.TimerDecorator class

Decorator class that adds a timer to the action
  *
@tparam I input
@tparam O output
  */
class TimerDecoratorScala[I, O] extends Action[I, O] {

  /**

  apply method
    Applies the action and prints the time it took to execute*
  @param function the action to be applied
  @param input    the input to the action
    */
  override def apply(function: JFunction[I, O], input: I): Unit = {
    val startTime = System.currentTimeMillis()// invoke the action
    function.apply(input)

    val endTime = System.currentTimeMillis()
    val duration = endTime - startTime
    println(s"Total execution time: $duration milliseconds")}
}