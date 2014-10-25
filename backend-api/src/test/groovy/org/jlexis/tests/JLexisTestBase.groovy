package org.jlexis.tests

class JLexisTestBase extends GroovyTestCase {

    /**
     * Tests the precondition for a method that requires all method parameters to be non-null. It is expected that
     * a NullPointerException is thrown if null is passed to at least one method parameter.
     *
     * As an example, if you want to test a method <code>concat(String a, String b)</code> which does not allow one
     * of its parameters to be null, you can use this test like so:
     *
     * <pre>
     *     requireAllParametersNonNull(['Hello, ', 'World']) { a, b ->
     *         concat(a, b)
     *} </pre>
     *
     * The test function will then call the tested method once for each parameter where each parameter is set null. Note
     * that none of the provided test parameter must be null since the method under test is called once with these parameters.
     * So in this example, there will be two invocations of the tested method:
     * <pre>
     *     concat(null, 'World')
     *     concat('Hello, ', null)
     * </pre>
     *
     * Each invocation is expected to fail with a NullPointerException.
     *
     * @param params list of valid non-null parameters to the method under test
     * @param test closure that calls the method under test passing all closure parameters as parameters to the method
     */
    void requireAllParametersNonNull(def params = [], Closure test) {
        test.call(params)

        params.eachWithIndex { it, i ->
            def testParams = []
            params.each { testParams << it }
            testParams[i] = null

            shouldFail(NullPointerException.class) {
                test.call(testParams)
            }
        }
    }
}
