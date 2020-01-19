package ir.shayan.addadi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_in.*

class InFragment : Fragment() {
    lateinit var list: ArrayList<Point>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list = ArrayList()
        initList()
        add.setOnClickListener {
            if (x.text.isEmpty() || fx.text.isEmpty()) return@setOnClickListener
            val xv = x.text.toString().toDouble()
            val fxv = fx.text.toString().toDouble()
            val point = Point(xv, fxv)
            list.add(point)
            recycler.adapter?.notifyItemInserted(list.size - 1)

            x.text.clear()
            fx.text.clear()
            x.requestFocus()
        }

        evaluate.setOnClickListener {
            if (list.isEmpty()) {
                Toast.makeText(context,R.string.error_in,Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val exp = daronyabNioton(
                list.map { it.x }.toDoubleArray(),
                list.map { it.fx }.toDoubleArray()
            )
            context?.apply {
                InDialog(this, exp).show()
            }
        }
    }

    private fun initList() {
        val adapter = InAdapter(list)
        recycler.adapter = adapter
    }
}