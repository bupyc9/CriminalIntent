package ru.bupyc9.criminalintent.ui.crime

import android.app.Activity
import android.content.Intent
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
import ru.bupyc9.criminalintent.ui.datecrime.DatePickerFragment

class CrimeFragment: Fragment() {
    companion object {
        @JvmStatic private val TAG = CrimeFragment::class.java.simpleName
        @JvmStatic private val ARG_CRIME = "arg_crime"
        @JvmStatic private val DIALOG_DATE = "dialog_date"
        @JvmStatic private val REQUEST_DATE = 0

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

        updateDate()
        crime_date.setOnClickListener {
            val dialog = DatePickerFragment.newInstance(mCrime.date)
            dialog.setTargetFragment(this, REQUEST_DATE)
            dialog.show(fragmentManager, DIALOG_DATE)

        }

        crime_solved.isChecked = mCrime.solved
        crime_solved.setOnCheckedChangeListener { _, isChecked -> mCrime.solved = isChecked }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_DATE) {
            val date = DatePickerFragment.getDate(data)
            mCrime.date = date
            updateDate()
        }
    }

    private fun updateDate() {
        crime_date.text = mCrime.date.toString()
    }
}