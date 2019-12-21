package ir.shayan.addadi

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mariuszgromada.math.mxparser.Expression

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var function: Expression

    @Before
    fun setup() {
        function = Expression("x^2 - 2")
    }

    @Test
    fun no_argument() {
        assertEquals(15.toDouble(), function.calculate(), 0.01)
    }

    @Test
    fun one_argument() {
        assertEquals((-8).toDouble(), function.eval(Pair("x", 1.toDouble())), 0.01)
        assertEquals((-15).toDouble(), function.eval(Pair("x", 0.toDouble())), 0.01)
    }

    @Test
    fun root() {
        assertEquals(1.5, function.findRoot(1.0, 2.0, 0.01), 0.05)
    }

    @Test
    fun root2() {
        assertEquals(1.5, function.findRoot2(1.0, 2.0, 0.01), 0.05)
    }

    @Test
    fun root3() {
        assertEquals(-1.0, function.findRoot3(1.5, 0.01), 0.05)
    }
}
