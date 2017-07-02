package ru.bupyc9.criminalintent.ui.datecrime

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.DatePicker
import ru.bupyc9.criminalintent.R
import java.util.*

class DatePickerFragment : DialogFragment() {
    companion object {
        @JvmStatic private val ARG_DATE = "arg_date"
        @JvmStatic private val EXTRA_DATE = "extra_date"

        @JvmStatic fun newInstance(date: Date): DatePickerFragment {
            val fragment = DatePickerFragment()
            val bundle = Bundle()

            bundle.putSerializable(ARG_DATE, date)

            fragment.arguments = bundle

            return fragment
        }

        @JvmStatic fun getDate(intent: Intent?): Date {
            return intent!!.getSerializableExtra(EXTRA_DATE) as Date
        }
    }

    private lateinit var mDate: Date
    private lateinit var mDatePicker: DatePicker

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        mDate = args!!.getSerializable(ARG_DATE) as Date
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)

        val calendar = Calendar.getInstance()
        calendar.time = mDate

        mDatePicker = view.findViewById(R.id.dialog_date)

        mDatePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null
        )

        return AlertDialog.Builder(activity)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, { dialog, which ->
                    val date = GregorianCalendar(
                            mDatePicker.year,
                            mDatePicker.month,
                            mDatePicker.dayOfMonth
                    )

                    sendResult(Activity.RESULT_OK, date.time)
                })
                .setView(view)
                .create()
    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()

        intent.putExtra(EXTRA_DATE, date)
        targetFragment.onActivityResult(targetRequestCode, resultCode, intent)
    }
}
