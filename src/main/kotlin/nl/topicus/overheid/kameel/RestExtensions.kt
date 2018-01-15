package nl.topicus.overheid.kameel

import org.apache.camel.model.RouteDefinition
import org.apache.camel.model.rest.RestDefinition
import org.apache.camel.model.rest.RestOperationParamDefinition
import org.apache.camel.model.rest.RestParamType
import kotlin.reflect.KClass

/**
 * Various Kotlin extensions for the REST-DSL of Apache Camel.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

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
fun RestDefinition.type(klass: KClass<*>): RestDefinition {
    return type(klass.java)
}

/**
 *
 * Alias for `[type]` with a more specific name which is more relatable to [outType].
 *
 * @param klass Class of the input type for the exchange.
 */
fun RestDefinition.inType(klass: Class<*>): RestDefinition {
    return type(klass)
}

/**
 * Alias for `[type]` with a more specific name which is more relatable to [outType].
 *
 * @param klass Class of the input type for the exchange.
 */
fun RestDefinition.inType(klass: KClass<*>): RestDefinition {
    return type(klass = klass)
}

/**
 * Overload for [RestDefinition.outType] accepting a `KClass<*>` instead of a `Class<?>`. Use of this overload is preferred but requires the use of a
 * named parameter, i.e.: `type(kclass = Unit::class)` instead of `type(Unit::class.java)`.
 *
 * @param klass Class of the output type for the exchange.
 */
fun RestDefinition.outType(klass: KClass<*>): RestDefinition {
    return outType(klass.java)
}

/**
 * Define a new parameter block. Use of this method is not recommended as the type of the parameter needs to be defined manually. Use of one of the
 * following methods is adviced instead:
 *  - [bodyParam] for a parameter passed in the HTTP body
 *  - [formDataParam] for a parameter passed as form data
 *  - [headerParam] for a parameter passed in a HTTP header
 *  - [pathParam] for a parameter passed in the URL path
 *  - [queryParam] for a query parameter
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.param(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition {
    return param()
            .apply(toApply)
            .endParam()
}

/**
 * Define a new paramter block for a body parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.bodyParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition {
    return typedParam(RestParamType.body, toApply)
}

/**
 * Define a new parameter block for a form data parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.formDataParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition {
    return typedParam(RestParamType.formData, toApply)
}

/**
 * Define a new parameter block for a header parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.headerParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition {
    return typedParam(RestParamType.header, toApply)
}

/**
 * Define a new parameter block for a path parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.pathParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition {
    return typedParam(RestParamType.path, toApply)
}

/**
 * Define a new parameter block for a query parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.queryParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition {
    return typedParam(RestParamType.query, toApply)
}

/**
 * Helper-method for the various typed parameters.
 *
 * @param type Type of the parameter block to create
 * @param toApply content of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
private fun RestDefinition.typedParam(type: RestParamType, toApply: RestOperationParamDefinition.() -> Unit): RestDefinition {
    return param()
            .type(type)
            .apply(toApply)
            .endParam()
}

/**
 * Define a subroute linked to the current RestDefintion
 *
 * @param toApply block of the subroute
 * @return returns back to the [RestDefinition] after adding the subroute
 */
fun RestDefinition.route(toApply: RouteDefinition.() -> Unit): RestDefinition {
    return route().apply(toApply).endRest()
}