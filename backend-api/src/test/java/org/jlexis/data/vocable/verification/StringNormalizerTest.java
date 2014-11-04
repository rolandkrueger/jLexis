package org.jlexis.data.vocable.verification;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.jlexis.data.vocable.verification.WhitespaceAndSuffixTolerantSet.StringNormalizer;

public class StringNormalizerTest {
    private StringNormalizer normalizer;
    @Before
    public void setUp() {
        normalizer = new StringNormalizer();
    }

    @Test
    public void test_normalize() {
        assertThat(normalizer.normalize(""), is(""));
        assertThat(normalizer.normalize("test value"), is("test value"));
        assertThat(normalizer.normalize("  test \t   value \n "), is("test value"));
        assertThat(normalizer.normalize("test. value"), is("test.value"));
        assertThat(normalizer.normalize("test\t.\nvalue"), is("test.value"));
        assertThat(normalizer.normalize(" test "), is("test"));
    }
}