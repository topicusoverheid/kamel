package nl.topicus.overheid.kamel

import org.apache.camel.Message
import kotlin.reflect.KClass

/**
 * Kotlin extensions for Apache Camel Messages.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/**
 * Gives the typed body of a message.
 * 
 * @param type Type of the body, can be `null`.
 */
fun <T: Any> Message.getBody(type: KClass<T>) : T? = getBody(type.java)

/**
 * Gives the typed body of a message, which cannot be `null`.
 *
 * @param type Type of the body, cannot be `null`.
 */
fun <T: Any> Message.surelyGetBody(type: KClass<T>) : T = getBody(type.java) as T