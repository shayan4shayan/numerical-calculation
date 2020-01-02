package ir.shayan.addadi

import android.app.AlertDialog
import android.content.Context
import kotlinx.android.synthetic.main.dialog_result.*
import java.lang.StringBuilder

class ResultDialog(context: Context, private val map: Map<String, String>) :
    AlertDialog(context) {
    override fun show() {
        super.show()
        setContentView(R.layout.dialog_result)
        btn_close.setOnClickListener { dismiss() }
        val content = StringBuilder().apply {
            map.keys.forEach { key ->
                append(key)
                append(" : ")
                append(map[key])
                append("\n")
            }
        }.toString()

        text_content.text = content
        setCancelable(false)
    }
}