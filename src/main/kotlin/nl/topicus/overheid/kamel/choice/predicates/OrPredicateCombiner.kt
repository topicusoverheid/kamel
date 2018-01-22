package nl.topicus.overheid.kamel.choice.predicates

import org.apache.camel.Predicate
import org.apache.camel.builder.PredicateBuilder

/**
 * Kotlin extensions which can be used to combine various predicates using an inclusive or.
 *
 * @author Bas Dalenoord
 * @since 1.0-SNAPSHOT
 */

/**
 * Class for combining various predicates where in the final predicate (created using [OrPredicateCombiner.combine]) _one_ of the predicates should
 * evaluate to `true` for the resulting predicate to be valid.
 */
class OrPredicateCombiner: PredicateCombiner() {
    /**
     * Allow further chaining of predicates in this combiner
     *
     * @param predicate the predicate to be added.
     */
    infix fun or(predicate: Predicate): OrPredicateCombiner {
        addPredicate(predicate)
        return this
    }

    /** Combine the various predicates into a single predicate resulting in an `inclusive or` predicate */
    override fun combine(): Predicate = PredicateBuilder.or(getPredicates())
}

/**
 * Allow chaining into a [OrPredicateCombiner] from a starting [Predicate].
 *
 * @param predicate Predicate to be combined with the predicate the [or] is being called upon
 */
infix fun Predicate.or(predicate: Predicate): OrPredicateCombiner = OrPredicateCombiner().or(this).or(predicate)