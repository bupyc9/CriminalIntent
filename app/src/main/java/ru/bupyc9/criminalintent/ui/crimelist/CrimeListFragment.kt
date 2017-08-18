package ru.bupyc9.criminalintent.ui.crimelist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_crime_list.*
import ru.bupyc9.criminalintent.CrimeActivity
import ru.bupyc9.criminalintent.R
import ru.bupyc9.criminalintent.ui.CrimeLab
import ru.bupyc9.criminalintent.ui.crime.CrimeFragment
import ru.bupyc9.criminalintent.ui.crime.CrimePagerFragment

class CrimeListFragment : Fragment() {
    companion object {
        @JvmStatic private val SAVED_SUBTITLE_VISIBLE = "subtitle"

        @JvmStatic fun newInstance(): CrimeListFragment {
            val fragment = CrimeListFragment()
            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var mAdapter: CrimeListAdapter
    private var mSubtitleVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        }
    }

    override fun onPause() {
        super.onPause()
        updateSubtitle()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_crime_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crime_list.layoutManager = LinearLayoutManager(activity)
        crime_list.setHasFixedSize(true)

        val activity = activity as CrimeActivity

        val crimes = CrimeLab.get(activity).getCrimes()

        mAdapter = CrimeListAdapter(crimes)
        mAdapter.setOnClickListener { _, crime ->
            activity.addFragment(
                    CrimePagerFragment.newInstance(crimes, crimes.indexOf(crime)),
                    true
            )
        }
        crime_list.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()

        inflater?.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu?.findItem(R.id.menu_item_show_subtitle)
        if (mSubtitleVisible) {
            subtitleItem?.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem?.setTitle(R.string.show_subtitle)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.menu_item_new_crime -> optionItemNewCrime()
        R.id.menu_item_show_subtitle -> updateSubtitle()
        else -> super.onOptionsItemSelected(item)
    }

    private fun optionItemNewCrime(): Boolean {
        val activity = activity as CrimeActivity
        activity.addFragment(CrimeFragment.newInstance(), true)

        return true
    }

    private fun updateSubtitle(): Boolean {
        mSubtitleVisible = !mSubtitleVisible
        activity.invalidateOptionsMenu()

        val activity = activity as AppCompatActivity
        var subtitle = resources.getQuantityString(R.plurals.subtitle_plural, mAdapter.itemCount, mAdapter.itemCount)
        if (!mSubtitleVisible) {
            subtitle = null
        }

        activity.supportActionBar?.subtitle = subtitle

        return true
    }
}