package ir.shayan.addadi

import android.app.AlertDialog
import android.content.Context
import kotlinx.android.synthetic.main.dialog_in.*
import org.mariuszgromada.math.mxparser.Expression

class InDialog(context: Context, val exp: Expression) : AlertDialog(context) {
    override fun show() {
        super.show()
        setContentView(R.layout.dialog_in)
        text.text = exp.expressionString
        draw.setOnClickListener {
            DrawDialog(context, exp.expressionString, -5.0, 5.0).show()
        }
    }
}