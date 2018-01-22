package nl.topicus.overheid.kamel.choice.predicates

import org.apache.camel.Predicate
import org.apache.camel.builder.PredicateBuilder

/**
 * Kotlin extensions which can be used to combine various predicates using an inclusive and.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/**
 * Class for combining various predicates where in the final predicate (created using [AndPredicateCombiner.combine]) _all_ of the predicates should
 * evaluate to `true` for the resulting predicate to be valid.
 *
 * @since 1.0-SNAPSHOT
 */
class AndPredicateCombiner: PredicateCombiner() {
    /**
     * Allow further chaining of predicates in this combiner
     *
     * @param predicate the predicate to be added.
     */
    infix fun and(predicate: Predicate): AndPredicateCombiner {
        addPredicate(predicate)
        return this
    }

    /** Combine the various predicates into a single predicate resulting in an `inclusive and` predicate */
    override fun combine(): Predicate = PredicateBuilder.and(getPredicates())
}

/**
 * Allow chaining into a [AndPredicateCombiner] from a starting [Predicate].
 *
 * @param predicate Predicate to be combined with the predicate the [and] is being called upon
 */
infix fun Predicate.and(predicate: Predicate): AndPredicateCombiner = AndPredicateCombiner().and(this).and(predicate)