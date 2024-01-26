package Decorator

import java.util.Map
import java.util.function.Function

/**
 * Class representing a decorator for memoization.
 *
 * @param <I> Type of input data for the function.
 * @param <O> Type of output data for the function.
 */
class MemoizationDecorator[I, O](cache: Map[String, O], function: Function[I, O]) extends Function[I, O] {

  /**
   * Applies the memoization decorator.
   *
   * @param input Input data for the function.
   */
  def apply(input: I): O = {
    val actionId = generateActionId(function, input)
    val result = if (cache.containsKey(actionId)) {
      val cachedResult = cache.get(actionId)
      println(s"Result from cache: $cachedResult")
      cachedResult
    } else {
      val computedResult = function.apply(input)
      cache.put(actionId, computedResult)
      println(s"Result not in cache. Adding to cache: $computedResult")
      computedResult
    }

    result
  }

  /**
   * Generates a unique identifier for the action based on input parameters.
   *
   * @param function Function to be executed.
   * @param input Input data for the function.
   * @return Unique identifier for the action.
   */
  private def generateActionId(function: Function[I, O], input: I): String = {
    s"${function.getClass.getSimpleName}_${input.toString}"
  }
}
