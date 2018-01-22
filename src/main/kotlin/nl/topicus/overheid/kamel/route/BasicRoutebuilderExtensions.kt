package nl.topicus.overheid.kamel.route

import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.RouteDefinition

/**
 * Various Kotlin extensions for the Apache Camel RouteBuilder-class.
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
    return from(uri)
        .routeId(routeId)
        .apply { toApply(this) }
}