package ru.bupyc9.criminalintent.ui.crime

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.widget.ImageView
import ru.bupyc9.criminalintent.R
import ru.bupyc9.criminalintent.getScaledBitmap
import java.io.File

class PhotoFragment : DialogFragment() {
    companion object {
        @JvmStatic private val ARG_FILE = "arg_file"

        @JvmStatic
        fun newInstance(file: File): PhotoFragment {
            val fragment = PhotoFragment()
            val bundle = Bundle()

            bundle.putSerializable(ARG_FILE, file)

            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var mFile: File

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        mFile = args!!.getSerializable(ARG_FILE) as File
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_photo, null)

        val bitmap = getScaledBitmap(mFile.path, activity)
        val dialogPhoto: ImageView = view.findViewById(R.id.dialog_photo)
        dialogPhoto.setImageBitmap(bitmap)

        return AlertDialog.Builder(activity)
                .setView(view)
                .setNegativeButton(R.string.button_close, { _, _ -> })
                .create()
    }
}