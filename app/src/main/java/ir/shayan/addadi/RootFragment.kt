package ir.shayan.addadi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_root.*
import org.mariuszgromada.math.mxparser.Expression
import java.lang.Exception

class RootFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateView(position)
            }

        }

        draw_formula.setOnClickListener {
            draw(formula.text.toString(), root_start.text.toString(), root_end.text.toString())
        }

        draw_derivative.setOnClickListener {
            draw(derivative.text.toString(), root_start.text.toString(), root_end.text.toString())
        }

        evaluate.setOnClickListener {
            displayResult(
                spinner.selectedItemPosition,
                formula.text.toString(),
                derivative.text.toString(),
                root_start.text.toString(),
                root_end.text.toString(),
                start_point.text.toString(),
                error.text.toString()
            )
        }
    }

    private fun displayResult(
        selectedItemPosition: Int,
        formula_text: String,
        derivative_text: String,
        root_start_text: String,
        root_end_text: String,
        start_point_text: String,
        error_text: String
    ) {
        if (error_text.isEmpty()) {
            error.error = getString(R.string.error_empty)
            return
        }
        when (selectedItemPosition) {
            0 -> displayHalfResult(formula_text, root_start_text, root_end_text, error_text)
            1 -> displayNabejaeiResult(formula_text, root_start_text, root_end_text, error_text)
            2 -> displaySimpleRepeatResult(formula_text, start_point_text, error_text)
            3 -> displayNewtonResult(formula_text, derivative_text, start_point_text, error_text)
        }
        reset()
    }

    private fun reset() {
        formula.setText("")
        derivative.setText("")
        root_start.setText("")
        root_end.setText("")
        start_point.setText("")
    }

    private fun displayNewtonResult(
        formulaText: String,
        derivativeText: String,
        startPointText: String,
        errorText: String
    ) {
        try {
            val result = Expression(formulaText).findRootNioton(
                startPointText.toDouble(),
                Expression(derivativeText), errorText.toDouble()
            )
            val eval = Expression(formulaText).eval(Pair("x", result))
            val map = mapOf(
                Pair(getString(R.string.formula), formulaText),
                Pair(getString(R.string.derivative), derivativeText),
                Pair(getString(R.string.start_point), startPointText),
                Pair(getString(R.string.error), errorText),
                Pair(getString(R.string.root), result.toString()),
                Pair(getString(R.string.eval), eval.toString())
            )
            ResultDialog(context!!, map).show()
        } catch (e: Exception) {
            Toast.makeText(context, getString(R.string.error_evaluate), Toast.LENGTH_LONG).show()
        }
    }

    private fun displaySimpleRepeatResult(
        formulaText: String,
        startPointText: String,
        errorText: String
    ) {
        try {
            val result = Expression(formulaText).findRoot3(
                startPointText.toDouble(),
                errorText.toDouble()
            )
            val eval = Expression(formulaText).eval(Pair("x", result))
            val map = mapOf(
                Pair(getString(R.string.formula), formulaText),
                Pair(getString(R.string.start_point), startPointText),
                Pair(getString(R.string.error), errorText),
                Pair(getString(R.string.root), result.toString()),
                Pair(getString(R.string.eval), eval.toString())
            )
            ResultDialog(context!!, map).show()
        } catch (e: Exception) {
            Toast.makeText(context, getString(R.string.error_evaluate), Toast.LENGTH_LONG).show()
        }
    }

    private fun displayNabejaeiResult(
        formulaText: String,
        rootStartText: String,
        rootEndText: String,
        errorText: String
    ) {
        try {
            val result = Expression(formulaText).findRoot2(
                rootStartText.toDouble(),
                rootEndText.toDouble(),
                errorText.toDouble()
            )
            val eval = Expression(formulaText).eval(Pair("x", result))
            val map = mapOf(
                Pair(getString(R.string.formula), formulaText),
                Pair(getString(R.string.root_start), rootStartText),
                Pair(getString(R.string.root_end), rootEndText),
                Pair(getString(R.string.error), errorText),
                Pair(getString(R.string.root), result.toString()),
                Pair(getString(R.string.eval), eval.toString())
            )
            ResultDialog(context!!, map).show()
        } catch (e: Exception) {
            Toast.makeText(context, getString(R.string.error_evaluate), Toast.LENGTH_LONG).show()
        }
    }

    private fun displayHalfResult(
        formulaText: String,
        rootStartText: String,
        rootEndText: String,
        errorText: String
    ) {
        try {
            val result = Expression(formulaText).findRoot(
                rootStartText.toDouble(),
                rootEndText.toDouble(),
                errorText.toDouble()
            )
            val eval = Expression(formulaText).eval(Pair("x", result))
            val map = mapOf(
                Pair(getString(R.string.formula), formulaText),
                Pair(getString(R.string.root_start), rootStartText),
                Pair(getString(R.string.root_end), rootEndText),
                Pair(getString(R.string.error), errorText),
                Pair(getString(R.string.root), result.toString()),
                Pair(getString(R.string.eval), eval.toString())
            )
            ResultDialog(context!!, map).show()
        } catch (e: Exception) {
            Toast.makeText(context, getString(R.string.error_evaluate), Toast.LENGTH_LONG).show()
        }
    }

    private fun draw(formula: String, start: String, end: String) {
        DrawDialog(context!!, formula, start.toDouble(), end.toDouble()).show()
    }

    private fun updateView(position: Int) {
        when (position) {
            0 -> monasef()
            1 -> nabejayi()
            2 -> T_sade()
            3 -> newton()


        }
    }

    private fun newton() {
        layout_derivative.visibility = View.VISIBLE
        start_point.visibility = View.VISIBLE
        root_end.visibility = View.GONE
        root_start.visibility = View.GONE
    }

    private fun T_sade() {
        layout_derivative.visibility = View.GONE
        start_point.visibility = View.VISIBLE
        root_end.visibility = View.GONE
        root_start.visibility = View.GONE

    }

    private fun nabejayi() {
        layout_derivative.visibility = View.GONE
        start_point.visibility = View.GONE
        root_end.visibility = View.VISIBLE
        root_start.visibility = View.VISIBLE
    }

    private fun monasef() {
        layout_derivative.visibility = View.GONE
        start_point.visibility = View.GONE
        root_end.visibility = View.VISIBLE
        root_start.visibility = View.VISIBLE
    }
}