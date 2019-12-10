package ir.shayan.addadi

import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Expression

fun Expression.eval(map: Pair<String, Double>) = apply {
    addArguments(Argument(map.first, map.second))
}.calculate()

fun Expression.findRoot(
    start: Double,
    end: Double,
    error: Double
): Pair<Double, Double>? {
    // if value start point and value of end point has same sign then there is no way to find root
    // between these two points.
    var startPoint = start
    var endPoint = end
    var startVal = eval(Pair("x", start))
    var endVal = eval(Pair("x", end))

    if (startVal * endVal > 0) return null

    var lastRoot: Double
    var currentRoot = 0.toDouble()

    var lastVal: Double

    do {
        lastRoot = currentRoot

        val newPoint = (endPoint + startPoint) / 2
        val newVal = eval(Pair("x", newPoint))

        if (newVal == 0.toDouble()) return Pair(newPoint, newVal)
        if (newVal * startVal < 0) {
            endPoint = newPoint
            endVal = newVal
        } else {
            startPoint = newPoint
            startVal = newVal
        }

        currentRoot = newPoint
        lastVal = newVal

    } while (lastRoot - currentRoot >= error)

    return Pair(lastRoot, lastVal)
}