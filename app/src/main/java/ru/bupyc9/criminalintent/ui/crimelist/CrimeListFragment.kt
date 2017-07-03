package ru.bupyc9.criminalintent.ui.crimelist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import ru.bupyc9.criminalintent.R
import kotlinx.android.synthetic.main.fragment_crime_list.*
import ru.bupyc9.criminalintent.CrimeActivity
import ru.bupyc9.criminalintent.models.Crime
import ru.bupyc9.criminalintent.ui.CrimeLab
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_crime_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crime_list.layoutManager = LinearLayoutManager(activity)
        crime_list.setHasFixedSize(true)

        val activity = activity as CrimeActivity

        mAdapter = CrimeListAdapter(CrimeLab.instance.crimes)
        mAdapter.setOnClickListener { _, crime ->
            activity.addFragment(
                    CrimePagerFragment.newInstance(
                            CrimeLab.instance.crimes as ArrayList<Crime>,
                            CrimeLab.instance.crimes.indexOf(crime)
                    ),
                    true
            )
        }
        crime_list.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.fragment_crime_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when {
        item?.itemId == R.id.menu_item_new_crime -> optionItemNewCrime()
        else -> super.onOptionsItemSelected(item)
    }

    private fun optionItemNewCrime(): Boolean {
        val crime = Crime(0, "", Date(), false)
        CrimeLab.instance.crimes.add(crime)
        val fragment = CrimePagerFragment.newInstance(
                CrimeLab.instance.crimes as ArrayList<Crime>,
                CrimeLab.instance.crimes.indexOf(crime)
        )
        val activity = activity as CrimeActivity
        activity.addFragment(fragment, true)

        return true
    }
}