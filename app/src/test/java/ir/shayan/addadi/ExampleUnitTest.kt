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

    var function: Expression? = null

    @Before
    fun setup() {
        function = Expression("x*2 + 5*x^2 -15")
    }

    @Test
    fun one_argument() {
        assertEquals((-8).toDouble(),function?.eval(Pair("x",1.toDouble())))
        assertEquals((-15).toDouble(),function?.eval(Pair("x",0.toDouble())))
    }

    @Test
    fun two_arguments() {

    }

    @Test
    fun three_arguments(){

    }
}
