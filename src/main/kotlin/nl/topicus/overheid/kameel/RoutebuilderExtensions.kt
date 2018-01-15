package nl.topicus.overheid.kameel

import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.OnExceptionDefinition
import org.apache.camel.model.ProcessorDefinition
import org.apache.camel.model.RouteDefinition
import org.apache.camel.model.rest.RestDefinition
import kotlin.reflect.KClass

/**
 * Various Kotlin extensions for the Apache Camel [RouteBuilder]-class.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/**
 * Creates a route from given URI, optionally with given ID which defaults to the URI otherwise, and applies given block on it.
 *
 * @param uri URI of the route to start.
 * @param routeId Optional ID for the route. If no ID is passed explicitly, the URI will be used as the ID by default.
 * @param toApply route block
 * @return [RouteDefinition] which will be handled by Camel.
 */
fun RouteBuilder.from(uri: String, routeId: String = uri, toApply: RouteDefinition.() -> Unit): RouteDefinition {
    return from(uri).routeId(routeId).apply { toApply(this) }
}

/**
 * Creates a new REST definition and applies given block on it.
 *
 * @param path Path of the REST-block.
 * @param toApply body of the REST-block.
 * @return [RestDefinition] which will be handled by Camel.
 */
fun RouteBuilder.rest(path: String, toApply: RestDefinition.() -> Unit): RestDefinition {
    return rest(path).apply(toApply)
}

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