package nl.topicus.overheid.kamel.route.rest

import org.apache.camel.model.rest.RestDefinition
import org.apache.camel.model.rest.RestOperationParamDefinition
import org.apache.camel.model.rest.RestParamType

/**
 * Various Kotlin extensions for working with parameters in the REST-DSL of Apache Camel.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

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
fun RestDefinition.bodyParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition = typedParam(RestParamType.body, toApply)

/**
 * Define a new parameter block for a form data parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.formDataParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition = typedParam(RestParamType.formData, toApply)

/**
 * Define a new parameter block for a header parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.headerParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition = typedParam(RestParamType.header, toApply)

/**
 * Define a new parameter block for a path parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.pathParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition = typedParam(RestParamType.path, toApply)

/**
 * Define a new parameter block for a query parameter.
 *
 * @param toApply body of the parameter block
 * @return returns back to the [RestDefinition] after adding the parameter and closing the parameter block.
 */
fun RestDefinition.queryParam(toApply: RestOperationParamDefinition.() -> Unit): RestDefinition = typedParam(RestParamType.query, toApply)

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