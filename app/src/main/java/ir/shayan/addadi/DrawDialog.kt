package ir.shayan.addadi

import android.app.AlertDialog
import android.content.Context
import kotlinx.android.synthetic.main.dialog_draw.*
import org.mariuszgromada.math.mxparser.Expression

class DrawDialog(context: Context, private val exp: String, val start: Double, val end: Double) :
    AlertDialog(context) {
    override fun show() {
        super.show()
        setContentView(R.layout.dialog_draw)
        val points = Expression(exp).drawPints(start, end, 200)
        drawer.setDrawPoints(points)
    }
}