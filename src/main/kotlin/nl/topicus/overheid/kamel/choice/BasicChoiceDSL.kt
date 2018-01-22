package nl.topicus.overheid.kamel.choice

import org.apache.camel.Predicate
import org.apache.camel.model.ChoiceDefinition
import org.apache.camel.model.ProcessorDefinition
import org.apache.camel.model.RouteDefinition

/**
 * Various Kotlin extensions for the choice-DSL of Apache Camel.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/**
 * [Content Based Router EIP](http://camel.apache.org/content-based-router.html):
 * Creates a choice of one or more predicates with an otherwise clause, using a lambda to populate the predicates.
 *
 * @param toApply lambda to be applied for populating the predicates
 * @return the builder for a choice expression
 */
fun RouteDefinition.choice(toApply: ChoiceDefinition.() -> Unit): ProcessorDefinition<*> {
    return choice()
        .apply(toApply)
        .end()
}

/**
 * Sets the predicate for the when node, and defines the various actions to take if given predicate evaluates to `true`.
 *
 * @param predicate Predicate to consider before executing the defined actions
 * @param toApply lambda to populate the actions to be executed when the predicate evaluates to `true`
 *
 * @see given
 * @see ChoiceDefinition.when
 */
private fun ChoiceDefinition.`when`(predicate: Predicate, toApply: ChoiceDefinition.() -> Unit): ChoiceDefinition {
    return `when`(predicate)
        .apply(toApply)
        .endChoice()
}

/**
 * Sets the predicate for the `when` node, and defines the various actions to take if given predicate evaluates to `true`.
 *
 * @param predicate Predicate to consider before executing the defined actions
 * @param toApply lambda to populate the actions to be executed when the predicate evaluates to `true`
 */
fun ChoiceDefinition.given(predicate: Predicate, toApply: ChoiceDefinition.() -> Unit): ChoiceDefinition = `when`(predicate, toApply)

/**
 * Defines the various actions to take if none of the predicates in the [ChoiceDefinition] evaluated to `true`.
 *
 * @param toApply lambda to populate the actions to be executed when the predicate evaluates to `true`
 */
fun ChoiceDefinition.otherwise(toApply: ChoiceDefinition.() -> Unit): ChoiceDefinition {
    return otherwise()
        .apply(toApply)
        .endChoice()
}