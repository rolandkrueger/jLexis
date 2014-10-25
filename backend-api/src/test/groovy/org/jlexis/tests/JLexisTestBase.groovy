package org.jlexis.tests

abstract class JLexisTestBase extends GroovyTestCase {

    /**
     * <p>Tests the precondition for a method that requires certain method parameters to be non-null. It is expected that
     * a NullPointerException is thrown if null is passed to the tested method as one of these parameters.</p>
     *
     * <p>As an example, if you want to test a method <code>concat(String a, String b, String optional)</code> which does not allow
     * the first two of its parameters to be null, you can use this test like so:</p>
     *
     * <pre>
     *     requireParametersNonNull(['Hello, ', 'World', null]) { a, b, optional ->
     *         obj.concat(a, b, optional)
     *} </pre>
     *
     * <p>The test function will then call the tested method once for each non-null closure parameter where the next parameter
     * is set null. It is important that for each parameter that must not be null, a non-null test value is provided.
     * All nullable parameters have to be provided as null in the closure parameters.</p>
     *
     * <p>So in this example, there will be two invocations of the tested method. The third invocation for parameter 'optional'
     * is skipped as this parameter is allowed to be null.</p>
     * <pre>
     *     concat(null, 'World')
     *     concat('Hello, ', null)
     * </pre>
     *
     * <p>Each invocation is expected to fail with a NullPointerException.</p>
     *
     * @param params list of valid parameters for the method under test. All method parameter which are allowed to be
     *          null need to be passed as null in this list
     * @param test closure that calls the method under test passing all closure parameters as parameters to the method
     */
    void requireParametersNonNull(def params = [], Closure test) {
        test.call(params)

        params.eachWithIndex { it, i ->
            if (!it) {
                return
            }
            def testParams = []
            params.each { testParams << it }
            testParams[i] = null

            shouldFail(NullPointerException.class) {
                test.call(testParams)
            }
        }
    }
}
