package nl.topicus.overheid.kamel.route.rest

import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.RouteDefinition
import org.apache.camel.model.rest.RestDefinition
import kotlin.reflect.KClass

/**
 * Various Kotlin extensions for the REST-DSL of Apache Camel.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/**
 * Creates a new REST definition and applies given block on it.
 *
 * @param path Path of the REST-block.
 * @param toApply body of the REST-block.
 * @return [RestDefinition] which will be handled by Camel.
 */
fun RouteBuilder.rest(path: String, toApply: RestDefinition.() -> Unit): RestDefinition = rest(path).apply(toApply)

/**
 * Defines a new block for a RESTful HTTP GET-call.
 *
 * @param path (Optional) path to be used for the call, if `null` or `blank` the call is considered as a call to the root of the [rest]-block.
 * @param toApply body of the `get` call
 * @return returns back to the [RestDefinition] after adding the `get`-block to it.
 */
fun RestDefinition.get(path: String = "", toApply: RestDefinition.() -> Unit): RestDefinition {
    return when {
        path.isBlank() -> get()
        else -> get(path)
    }.apply(toApply)
}

/**
 * Defines a new block for a RESTful HTTP POST-call.
 *
 * @param path (Optional) path to be used for the call, if `null` or `blank` the call is considered as a call to the root of the [rest]-block.
 * @param toApply body of the `post` call
 * @return returns back to the [RestDefinition] after adding the `post`-block to it.
 */
fun RestDefinition.post(path: String = "", toApply: RestDefinition.() -> Unit): RestDefinition {
    return when {
        path.isBlank() -> post()
        else -> post(path)
    }.apply(toApply)
}

/**
 * Defines a new block for a RESTful HTTP PUT-call.
 *
 * @param path (Optional) path to be used for the call, if `null` or `blank` the call is considered as a call to the root of the [rest]-block.
 * @param toApply body of the `put` call
 * @return returns back to the [RestDefinition] after adding the `put`-block to it.
 */
fun RestDefinition.put(path: String = "", toApply: RestDefinition.() -> Unit): RestDefinition {
    return when {
        path.isBlank() -> put()
        else -> put(path)
    }.apply(toApply)
}

/**
 * Defines a new block for a RESTful HTTP PATCH-call.
 *
 * @param path (Optional) path to be used for the call, if `null` or `blank` the call is considered as a call to the root of the [rest]-block.
 * @param toApply body of the `patch` call
 * @return returns back to the [RestDefinition] after adding the `patch`-block to it.
 */
fun RestDefinition.patch(path: String = "", toApply: RestDefinition.() -> Unit): RestDefinition {
    return when {
        path.isBlank() -> patch()
        else -> patch(path)
    }.apply(toApply)
}

/**
 * Defines a new block for a RESTful HTTP DELETE-call.
 *
 * @param path (Optional) path to be used for the call, if `null` or `blank` the call is considered as a call to the root of the [rest]-block.
 * @param toApply body of the `delete` call
 * @return returns back to the [RestDefinition] after adding the `delete`-block to it.
 */
fun RestDefinition.delete(path: String = "", toApply: RestDefinition.() -> Unit): RestDefinition {
    return when {
        path.isBlank() -> delete()
        else -> delete(path)
    }.apply(toApply)
}

/**
 * Overload for [RestDefinition.type] accepting a `KClass<*>` instead of a `Class<?>`. Use of this overload is preferred but requires the use of a
 * named parameter, i.e.: `type(kclass = Unit::class)` instead of `type(Unit::class.java)`.
 *
 * @param klass Class of the input type for the exchange.
 * @see inType
 */
fun RestDefinition.type(klass: KClass<*>): RestDefinition = type(klass.java)

/**
 *
 * Alias for `[type]` with a more specific name which is more relatable to [outType].
 *
 * @param klass Class of the input type for the exchange.
 */
fun RestDefinition.inType(klass: Class<*>): RestDefinition = type(klass)

/**
 * Alias for `[type]` with a more specific name which is more relatable to [outType].
 *
 * @param klass Class of the input type for the exchange.
 */
fun RestDefinition.inType(klass: KClass<*>): RestDefinition = type(klass = klass)

/**
 * Overload for [RestDefinition.outType] accepting a `KClass<*>` instead of a `Class<?>`. Use of this overload is preferred but requires the use of a
 * named parameter, i.e.: `type(kclass = Unit::class)` instead of `type(Unit::class.java)`.
 *
 * @param klass Class of the output type for the exchange.
 */
fun RestDefinition.outType(klass: KClass<*>): RestDefinition = outType(klass.java)

/**
 * Define a subroute linked to the current RestDefintion
 *
 * @param toApply block of the subroute
 * @return returns back to the [RestDefinition] after adding the subroute
 */
fun RestDefinition.route(toApply: RouteDefinition.() -> Unit): RestDefinition = route().apply(toApply).endRest()