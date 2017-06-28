package ru.bupyc9.criminalintent.ui

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

        @JvmStatic fun newInstance(): CrimeFragment {
            val fragment = CrimeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle

            return fragment
        }
    }

    private val mCrime = Crime(1, "")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_crime, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crime_title.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(c: Editable?) {

            }

            override fun beforeTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mCrime.title = c.toString()
            }
        })
    }
}