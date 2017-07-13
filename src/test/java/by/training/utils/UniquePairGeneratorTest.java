package by.training.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class UniquePairGeneratorTest {

    UniquePairNumbersGenerator generator;
    final int maxNumber = 9;
    int[] pair;

    @Before
    public void init() {
        generator = new UniquePairNumbersGenerator(maxNumber);
        pair = generator.getPair();
    }

    @Test
    public void testGeneratedNumbersGreaterThenOne() {
        Assert.assertThat("Generated numbers > 1", (pair[0] >= 1) && (pair[1] >= 1), is(true));
    }

    @Test
    public void testGeneratedNumbersNotGreaterThenMaxNumber() {
        Assert.assertThat("Generated numbers <= maxNumber", (pair[0] <= maxNumber) && (pair[1] <= maxNumber), is(true));
    }

    @Test
    public void testGeneratedNumbersNotEqual() {
        Assert.assertThat("Generated numbers are unique", pair[0], not(pair[1]));
    }
}
