package nl.topicus.overheid.kamel

import org.apache.camel.Exchange
import org.apache.camel.Message
import kotlin.reflect.KClass

/**
 * Kotlin extensions for Apache Camel Exchanges.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/**
 * Alias for `exchange.in`, as `in` requires backticks because it's a reserved keyword in Kotlin.
 */
val Exchange.input: Message
    get() = this.`in`

/**
 * Alias for `exchange.out` to provide a similar API as for `exchange.input`.
 *
 * @see Exchange.input
 */
val Exchange.output: Message
    get() = this.out

/**
 * Set one or more properties for an exchange.
 *
 * Usage: `exchange.set("foo" to "bar", "baz" to "qux")`
 *
 * @param pairs (list of) pair(s) to convert into properties
 */
fun Exchange.set(vararg pairs: Pair<String, Any?>) {
    pairs.forEach { setProperty(it.first, it.second) }
}

/**
 * Get the exchange property associated with given name and type.
 *
 * @param name Name of the property to retrieve
 * @param type Type of the property
 * @return the property's value, or `null` when the property is not found or couldn't be converted to given type
 */
fun <T> Exchange.get(name: String, type: Class<T>): T? {
    return getProperty(name, type)
}

/**
 * Get the exchange property associated with given name and type.
 *
 * @param name Name of the property to retrieve
 * @param type Type of the property
 * @return the property's value, or `null` when the property is not found or couldn't be converted to given type
 */
fun <T : Any> Exchange.get(name: String, type: KClass<T>): T? {
    return get(name, type.java)
}

/**
 * Get the exchange property associated with given name and type, the property cannot be `null`.
 *
 * @param name Name of the property to retrieve
 * @param type Type of the property
 * @return the property's value
 */
fun <T> Exchange.surelyGet(name: String, type: Class<T>): T {
    return getProperty(name, type) as T
}

/**
 * Get the exchange property associated with given name and type, the property cannot be `null`.
 *
 * @param name Name of the property to retrieve
 * @param type Type of the property
 * @return the property's value
 */
fun <T: Any> Exchange.surelyGet(name: String, type: KClass<T>): T {
    return surelyGet(name, type.java)
}

/**
 * Gets the exchange property associated with given name
 */
fun Exchange.get(name: String): Any? {
    return getProperty(name)
}