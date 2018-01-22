package nl.topicus.overheid.kamel.route

import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.OnExceptionDefinition
import org.apache.camel.model.ProcessorDefinition
import kotlin.reflect.KClass

/**
 * Various Kotlin extensions for working with exceptions in Camel routes.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/**
 * Create a new exception handling definition and applies given block on it.
 *
 * @param exception Class of the exception to be handled
 * @param toApply body of the [OnExceptionDefinition] block
 * @return [OnExceptionDefinition] which will be handled by Camel.
 */
fun RouteBuilder.onException(exception: Class<out Throwable>, toApply: OnExceptionDefinition.() -> Unit): ProcessorDefinition<*> {
    return onException(exception, toApply, false)
}

/**
 * Create a new exception handling definition and applies given block on it.
 *
 * @param exception Class of the exception to be handled
 * @param toApply body of the [OnExceptionDefinition] block
 * @return [OnExceptionDefinition] which will be handled by Camel.
 */
fun RouteBuilder.onException(exception: KClass<out Throwable>, toApply: OnExceptionDefinition.() -> Unit): ProcessorDefinition<*> {
    return onException(exception.java, toApply, false)
}

/**
 * Create a new exception handling defintion which marks the exception as handled afterwards.
 *
 * @param exception Class of the exception to be handled
 * @param toApply body of the [OnExceptionDefinition] block
 * @return [OnExceptionDefinition] which will be handled by Camel.
 */
fun RouteBuilder.handleException(exception: KClass<out Throwable>, toApply: OnExceptionDefinition.() -> Unit): ProcessorDefinition<*> {
    return onException(exception.java, toApply, true)
}

/**
 * Shared method for defining various exception handling patterns above.
 *
 * @param exception Class of the exception to be handled
 * @param toApply body of the [OnExceptionDefinition] block
 * @param markAsHandled `true` if the exception should be marked as handled, `false` when it should be left untouched.
 * @return [OnExceptionDefinition] which will be handled by Camel.
 */
private fun RouteBuilder.onException(exception: Class<out Throwable>, toApply: OnExceptionDefinition.() -> Unit,
                                     markAsHandled: Boolean): ProcessorDefinition<*> {
    val definition = onException(exception).apply(toApply)
    if(markAsHandled) {
        definition.handled(true)
    }
    return definition.end()
}