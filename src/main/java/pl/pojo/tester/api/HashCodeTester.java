package pl.pojo.tester.api;


import java.util.List;
import java.util.function.Consumer;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

/**
 * HashCodeTester tests classes if the implementation of {@code hashCode} method is good.
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public class HashCodeTester extends AbstractTester {

    /**
     * {@inheritDoc}
     */
    public HashCodeTester() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public HashCodeTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class<?> testedClass = baseClassAndFieldPredicatePair.getClazz();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        shouldHaveSameHashCodes(instance);
        shouldHaveSameHashCodesWithDifferentInstance(instance);
        shouldHaveDifferentHashCodes(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
    }

    private void shouldHaveSameHashCodes(final Object object) {
        testAssertions.assertThatHashCodeMethodFor(object)
                      .isConsistent();
    }

    private void shouldHaveSameHashCodesWithDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.generateSameInstance(object);
        testAssertions.assertThatHashCodeMethodFor(object)
                      .returnsSameValueFor(otherObject);
    }


    private void shouldHaveDifferentHashCodes(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                                              final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final List<Object> differentObjects = objectGenerator.generateDifferentObjects(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
        final Object firstObject = differentObjects.remove(0);
        differentObjects.forEach(assertHaveDifferentHashCodes(firstObject));
    }

    private Consumer<Object> assertHaveDifferentHashCodes(final Object object) {
        return eachDifferentObject -> testAssertions.assertThatHashCodeMethodFor(object)
                                                    .returnsDifferentValueFor(eachDifferentObject);
    }

}
