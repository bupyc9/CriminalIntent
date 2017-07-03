package ru.bupyc9.criminalintent.ui.crime

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.bupyc9.criminalintent.R
import kotlinx.android.synthetic.main.fragment_pager_crime.*
import ru.bupyc9.criminalintent.models.Crime

class CrimePagerFragment: Fragment() {
    companion object {
        @JvmStatic private val TAG = CrimePagerFragment::class.java.simpleName
        @JvmStatic private val ARG_CRIMES = "arg_crimes"
        @JvmStatic private val ARG_POSITION = "arg_position"

        @JvmStatic fun newInstance (crimes: ArrayList<Crime>, position: Int): CrimePagerFragment {
            val fragment = CrimePagerFragment()
            val bundle = Bundle()

            bundle.putParcelableArrayList(ARG_CRIMES, crimes)
            bundle.putInt(ARG_POSITION, position)

            fragment.arguments = bundle

            return fragment
        }
    }

    private var mPosition = 0
    private var mCrimes: ArrayList<Crime> = arrayListOf()


    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        mPosition = args!!.getInt(ARG_POSITION)
        mCrimes = args.getParcelableArrayList(ARG_CRIMES)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pager_crime, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter: FragmentStatePagerAdapter = object : FragmentStatePagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment = CrimeFragment.newInstance(mCrimes[position])

            override fun getCount(): Int = mCrimes.size
        }

        crime_pager.adapter = adapter
        crime_pager.currentItem = mPosition
    }
}