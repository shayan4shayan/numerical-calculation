package ir.shayan.addadi

import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Expression
import java.lang.StringBuilder


fun Expression.eval(map: Pair<String, Double>) = apply {
    removeAllArguments()
    addArguments(Argument(map.first, map.second))
}.calculate()

fun Expression.findRoot(
    start: Double,
    end: Double,
    error: Double
): Double {
    // if value start point and value of end point has same sign then there is no way to find root
    // between these two points.
    var startPoint = start
    var endPoint = end
    var startVal = eval(Pair("x", start))
    val endVal = eval(Pair("x", end))

    require(startVal * endVal <= 0) { "start and end points has same sign" }

    var lastRoot: Double
    var currentRoot = 0.0
    do {
        lastRoot = currentRoot

        val newPoint = (endPoint + startPoint) / 2
        val newVal = eval(Pair("x", newPoint))
        println("$newPoint")
        println("$newVal")

        if (newVal == 0.toDouble()) return newPoint
        if (newVal * startVal < 0) {
            endPoint = newPoint

        } else {
            startPoint = newPoint
            startVal = newVal
        }

        currentRoot = newPoint

        println("error = " + (lastRoot - currentRoot))
    } while (kotlin.math.abs(lastRoot - currentRoot) >= error)

    return lastRoot
}

fun Expression.findRoot2(
    start: Double,
    end: Double,
    error: Double
): Double {

    var startPoint = start
    var endPoint = end
    var startVal = eval(Pair("x", start))
    var endVal = eval(Pair("x", end))

    require(startVal * endVal <= 0) { "start and end points has same sign" }

    var lastRoot: Double
    var currentRoot = 0.0
    do {
        lastRoot = currentRoot

        val newPoint = (startPoint * endVal - endPoint * startVal) / (endVal - startVal)
        val newVal = eval(Pair("x", newPoint))
        println("$newPoint")
        println("$newVal")

        if (newVal == 0.toDouble()) return newPoint
        if (newVal * startVal < 0) {
            endPoint = newPoint
            endVal = newVal
        } else {
            startPoint = newPoint
            startVal = newVal
        }

        currentRoot = newPoint

        println("error = " + (lastRoot - currentRoot))
    } while (kotlin.math.abs(lastRoot - currentRoot) >= error)

    return lastRoot
}

fun Expression.findRoot3(
    start: Double,
    error: Double
): Double {

    val maxSteps = 100

    var startPoint = start

    val ne = Expression("$expressionString - x")

    var lastRoot: Double
    var currentRoot = startPoint
    var step = 0
    do {
        step++
        lastRoot = currentRoot

        val newPoint = eval(Pair("x", currentRoot))
        println("x= $newPoint")
        currentRoot = newPoint

        println("error = " + (lastRoot - currentRoot))
    } while (kotlin.math.abs(lastRoot - currentRoot) >= error && step < maxSteps)

    return currentRoot
}

fun Expression.findRootNioton(start: Double, moshtag: Expression, error: Double): Double {
    val maxStep = 100

    var lastRoot: Double
    var currentRoot = start
    var step = 0

    do {
        step++
        lastRoot = currentRoot
        val newPoint =
            currentRoot - (eval(Pair("x", currentRoot)) / moshtag.eval(Pair("x", currentRoot)))
        currentRoot = newPoint
    } while (lastRoot - currentRoot >= error && step < maxStep)

    return currentRoot
}

fun Expression.drawPints(start: Double, end: Double, numPoints: Int): List<Pair<Double, Double>> {
    require(start < end) { "start point must be less than end point" }
    val step = (end - start) / numPoints
    val points = ArrayList<Pair<Double, Double>>()
    points.addAll((0 until numPoints).map { start + it * step }.map {
        Pair(
            it,
            eval(Pair("x", it))
        )
    })
    return points
}

fun daronYabLagrange(x: DoubleArray, fx: DoubleArray): Expression {
    return Expression().apply {
        var out = doubleArrayOf(0.0)
        for (index in x.indices) {
            out = sum(out, multiply(lagrange(x,index), fx[index]))
        }
        expressionString = StringBuilder().apply {
            for (index in out.indices){
                if (index!=0){
                    append("+")
                }
                append(out[index])
                append("*")
                append("x^$index")
            }
        }.toString()
    }
}

private fun lagrange(x: DoubleArray, i: Int): DoubleArray {
    var res = doubleArrayOf(1.0)
    for (item in x) {
        res = multiply(res, doubleArrayOf(item, 1.0))
    }
    return res
}

private fun multiply(a: DoubleArray, b: DoubleArray): DoubleArray {
    val prod = DoubleArray(a.size + b.size - 1)
    for (i in 0 until a.size + b.size - 1) {
        prod[i] = 0.0
    }

    for (i in a.indices) {
        for (j in b.indices) {
            prod[i + j] += a[i] * b[j]
        }
    }

    return prod
}

private fun multiply(x: DoubleArray, y: Double): DoubleArray {
    return DoubleArray(x.size).apply {
        x.forEach {
            set(x.indexOf(it), it * y)
        }
    }
}

private fun sum(x: DoubleArray, y: DoubleArray): DoubleArray {
    return DoubleArray(x.size).apply {
        for (i in x.indices) {
            set(i, x[i] + y[i])
        }
    }
}