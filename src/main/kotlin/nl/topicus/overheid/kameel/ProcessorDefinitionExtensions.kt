package nl.topicus.overheid.kameel

import org.apache.camel.Exchange
import org.apache.camel.Expression
import org.apache.camel.model.ProcessorDefinition
import org.apache.camel.model.SplitDefinition
import org.apache.camel.model.dataformat.JsonLibrary
import kotlin.reflect.KClass

/**
 * <a href="http://camel.apache.org/message-translator.html">Message Translator EIP:</a>
 * Adds a bean which is invoked which could be a final destination, or could be a transformation in a pipeline.
 *
 * This method forms an overload for the [bean]-method accepting a `Class<?>` and exposes an API accepting a `KClass<*>` instead. _This only works
 * when you use named parameters, i.e.: `bean(kclass = Unit::class)` instead of `bean(Unit::class.java)`_.
 *
 * @param beanType  the bean class, Camel will instantiate an object at runtime
 * @return the builder
 * @see ProcessorDefinition.bean
 */
@Suppress("EXTENSION_SHADOWED_BY_MEMBER") // TODO: Define a different named alias for this method.
fun ProcessorDefinition<*>.bean(klass: KClass<*>): ProcessorDefinition<*> {
    return bean(klass.java)
}

/**
 * Allows to define an Exchange processor with the exchange defined as it's receiver, removing the need to explicitly define the `exchange`-
 * parameter for your processor function.
 *
 * @param toApply body of the processor
 * @return returns back to the [ProcessorDefinition] after adding the processor.
 */
fun ProcessorDefinition<*>.processExchange(toApply: Exchange.() -> Unit): ProcessorDefinition<*> {
    return process { exchange ->
        with(exchange, toApply)
    }
}

/**
 * Allows a [Exchange.getIn] call with a Kotlin-class instead of a Java-class. _This only works when you use named parameters, i.e.:
 * `getIn(kclass = Unit::class)` instead of `getIn(Unit::class.java)`._
 *
 * @param klass Class of the input body
 */
inline fun <reified T : Any> Exchange.getIn(klass: KClass<T>): T {
    return getIn(klass.java)
}

/**
 * Allows a simpler call to marshal with given [JsonLibrary], instead of chaining `marshal()` together with `json(jsonLibrary)` manually.
 *
 * @param jsonLibrary library to be used
 * @return returns back to the [ProcessorDefinition] after adding the marshaller.
 */
fun ProcessorDefinition<*>.marshal(jsonLibrary: JsonLibrary): ProcessorDefinition<*> {
    return marshal().json(jsonLibrary)
}

/**
 * Allows to define a splitter with a block definition for the splitted processing.
 * TODO: This definition still clashes with [SplitDefinition.split] using an AggregationStrategy, change the name of
 *
 * @param expression Expression to split
 * @param toApply block to be applied the the splitter
 * @return returns back to the [ProcessorDefinition] after adding the splitter.
 */
fun ProcessorDefinition<*>.split(expression: Expression, toApply: SplitDefinition.() -> Unit): ProcessorDefinition<*> {
    return split(expression).apply(toApply).end()
}