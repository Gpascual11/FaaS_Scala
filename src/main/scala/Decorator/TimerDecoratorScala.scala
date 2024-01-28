package Decorator

import java.util.function.Function

/**
 * TimerDecorator class
 * Decorator class that adds a timer to the action
 *
 * @tparam I input
 * @tparam O output
 */
class TimerDecoratorScala[I, O](function: Function[I, O]) extends Function[I, O] {

  /**
   * apply method
   * Applies the action and prints the time it took to execute
   *
   * @param input the input to the action
   * @return the result of the action
   */
  def apply(input: I): O = {
    val startTime = System.currentTimeMillis()

    val result = function.apply(input)

    val endTime = System.currentTimeMillis()
    val duration = endTime - startTime

    println(s"Total execution time: $duration milliseconds")
    result
  }

}
