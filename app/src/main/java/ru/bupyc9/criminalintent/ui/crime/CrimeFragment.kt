package ru.bupyc9.criminalintent.ui.crime

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.bupyc9.criminalintent.models.Crime
import kotlinx.android.synthetic.main.fragment_crime.*
import ru.bupyc9.criminalintent.R

class CrimeFragment: Fragment() {
    companion object {
        @JvmStatic private val TAG = CrimeFragment::class.java.simpleName
        @JvmStatic private val ARG_CRIME = "arg_crime"

        @JvmStatic fun newInstance(crime: Crime): CrimeFragment {
            val fragment = CrimeFragment()
            val bundle = Bundle()

            bundle.putParcelable(ARG_CRIME, crime)
            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var mCrime: Crime

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        mCrime = args?.getParcelable(ARG_CRIME)!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_crime, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crime_title.setText(mCrime.title)

        crime_title.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(c: Editable?) {

            }

            override fun beforeTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mCrime.title = c.toString()
            }
        })

        crime_date.text = mCrime.date.toString()
        crime_date.isEnabled = mCrime.solved

        crime_solved.setOnCheckedChangeListener { _, isChecked -> mCrime.solved = isChecked }
    }
}