package nl.topicus.overheid.kamel.choice.predicates

import nl.topicus.overheid.kamel.choice.given
import org.apache.camel.Predicate
import org.apache.camel.model.ChoiceDefinition

/**
 * Abstraction for Kotlin extensions which can be used to combine various predicates using different combination strategies
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/** Base class for combiners allowing you to combine various predicates in a [given]-call using a more concise syntax. */
abstract class PredicateCombiner {
    private val predicates: MutableList<Predicate> = mutableListOf()

    /** Add given predicate to the list of all predicates in this combiner */
    protected fun addPredicate(toAdd: Predicate) = predicates.add(toAdd)

    /**
     * Combine the various predicates (accessible via [getPredicates]) using a subclass-specific strategy (such as inclusive and or inclusive or).
     * @see getPredicates
     */
    abstract fun combine(): Predicate

    /**
     * Protected getter for accessing the predicates in a non-mutable way. To add a predicate, refer to [addPredicate]
     * @see addPredicate
     */
    protected fun getPredicates(): List<Predicate> = predicates
}

/**
 * Allows a [given]-call to be started with a given [PredicateCombiner]. The given [PredicateCombiner] will be combined before passing it as a
 * single predicate to the default [ChoiceDefinition.when]-call
 */
fun ChoiceDefinition.given(predicateCombiner: PredicateCombiner, toApply: ChoiceDefinition.() -> Unit): ChoiceDefinition {
    return given(predicateCombiner.combine(), toApply)
}