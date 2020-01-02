package ir.shayan.addadi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PagerAdapter(supportFragmentManager, getFragmentList())
        viewPager.adapter = adapter

        tab.setupWithViewPager(viewPager)

    }

    private fun getFragmentList() = ArrayList<PagerItem>().apply {
        add(
            PagerItem(
                RootFragment(), getString(R.string.root_finding)
            )
        )
        add(
            PagerItem(
                InFragment(), getString(R.string.finding_polynomial_function)
            )
        )
    }
}
