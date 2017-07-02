package ru.bupyc9.criminalintent.ui.crimelist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.bupyc9.criminalintent.R
import kotlinx.android.synthetic.main.fragment_crime_list.*
import ru.bupyc9.criminalintent.CrimeActivity
import ru.bupyc9.criminalintent.models.Crime
import ru.bupyc9.criminalintent.ui.crime.CrimePagerFragment
import java.util.*
import kotlin.collections.ArrayList

class CrimeListFragment : Fragment() {
    companion object {
        @JvmStatic fun newInstance(): CrimeListFragment {
            val fragment = CrimeListFragment()
            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var mAdapter: CrimeListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_crime_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crime_list.layoutManager = LinearLayoutManager(activity)
        crime_list.setHasFixedSize(true)

        val crimes: MutableList<Crime> = mutableListOf()

        (1..100).mapTo(crimes) {
            Crime(
                    it,
                    "Title $it",
                    Date(),
                    false
            )
        }

        val activity = activity as CrimeActivity

        mAdapter = CrimeListAdapter(crimes)
        mAdapter.setOnClickListener { _, crime -> activity.addFragment(CrimePagerFragment.newInstance(crimes as ArrayList<Crime>, crimes.indexOf(crime)), true) }
        crime_list.adapter = mAdapter
    }
}