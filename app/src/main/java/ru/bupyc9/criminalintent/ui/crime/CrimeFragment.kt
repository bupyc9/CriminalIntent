package ru.bupyc9.criminalintent.ui.crime

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.ShareCompat
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import ru.bupyc9.criminalintent.models.Crime
import kotlinx.android.synthetic.main.fragment_crime.*
import kotlinx.android.synthetic.main.part_camera_and_title.*
import ru.bupyc9.criminalintent.R
import ru.bupyc9.criminalintent.getScaledBitmap
import ru.bupyc9.criminalintent.ui.CrimeLab
import ru.bupyc9.criminalintent.ui.datecrime.DatePickerFragment
import java.io.File
import java.util.*

class CrimeFragment : Fragment() {
    companion object {
        @JvmStatic private val TAG = CrimeFragment::class.java.simpleName
        @JvmStatic private val ARG_CRIME_ID = "arg_crime"
        @JvmStatic private val DIALOG_DATE = "dialog_date"
        @JvmStatic private val REQUEST_DATE = 0
        @JvmStatic private val REQUEST_CONTACT = 1
        @JvmStatic private val REQUEST_PHOTO = 2

        @JvmStatic
        fun newInstance(): CrimeFragment = CrimeFragment()

        @JvmStatic
        fun newInstance(id: Int): CrimeFragment {
            val fragment = CrimeFragment()
            val bundle = Bundle()

            bundle.putInt(ARG_CRIME_ID, id)
            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var mCrime: Crime
    private var mId: Int = 0
    private var mPhotoFile: File? = null

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        mId = args?.getInt(ARG_CRIME_ID)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        Log.d(TAG, "onCreate");

        if (mId > 0) {
            mCrime = CrimeLab.get(activity).getCrime(mId)!!
        } else {
            mCrime = Crime(0, "", Date(), false, "")
        }

        mPhotoFile = CrimeLab.get(activity).getPhotoFile(mCrime)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()

        inflater?.inflate(R.menu.fragment_crime, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.menu_save_crime -> save()
        else -> super.onOptionsItemSelected(item)
    }

    private fun save(): Boolean {
        Log.d(TAG, "click by save button");

        var verify = true

        if (mCrime.title.isEmpty()) {
            verify = false
            crime_title.error = getString(R.string.error_crime_empty_title)
        }

        if (verify) {
            val crimeLab = CrimeLab.get(activity)
            if (mCrime.id > 0) {
                crimeLab.updateCrime(mCrime)
            } else {
                crimeLab.addCrime(mCrime)
            }

            activity.onBackPressed()
        }

        return true
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_crime, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crime_title.setText(mCrime.title)

        crime_title.addTextChangedListener(object : TextWatcher {
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

        crime_report.setOnClickListener {
            val builder = ShareCompat.IntentBuilder.from(activity)
            builder
                    .setType("text/plain")
                    .setText(getCrimeReport())
                    .setSubject(getString(R.string.crime_report_subject))
                    .setChooserTitle(R.string.send_report)

            startActivity(builder.createChooserIntent())
        }

        val pickContact = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        crime_suspect.setOnClickListener {
            startActivityForResult(pickContact, REQUEST_CONTACT)
        }

        if (!mCrime.suspect.isEmpty()) {
            crime_suspect.text = mCrime.suspect
        }

        val packageManager = activity.packageManager
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            crime_suspect.isEnabled = false
        }

        val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null
        crime_camera.isEnabled = canTakePhoto
        if (canTakePhoto) {
            val uri = Uri.fromFile(mPhotoFile)
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }

        crime_camera.setOnClickListener {
            startActivityForResult(captureImage, REQUEST_PHOTO)
        }
        updatePhotoView()
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

        if (requestCode == REQUEST_CONTACT && data != null) {
            val contactUri: Uri = data.data
            val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
            val cursor = activity.contentResolver.query(contactUri, queryFields, null, null, null)
            try {
                if (cursor.count == 0) {
                    return
                }

                cursor.moveToFirst()
                val suspect = cursor.getString(0)
                mCrime.suspect = suspect
                crime_suspect.text = suspect
            } finally {
                cursor.close()
            }
        }

        if (requestCode == REQUEST_PHOTO) {
            updatePhotoView()
        }
    }

    private fun updateDate() {
        crime_date.text = mCrime.date.toString()
    }

    override fun onDetach() {
        super.onDetach()

        Log.d(TAG, "onDetach")
    }

    private fun getCrimeReport(): String {
        var solvedString = ""
        if (mCrime.solved) {
            solvedString = getString(R.string.crime_report_solved)
        } else {
            solvedString = getString(R.string.crime_report_unsolved)
        }

        val dateString = DateFormat.format("EEE, MMM dd", mCrime.date).toString()

        var suspect = mCrime.suspect
        if (suspect.isEmpty()) {
            suspect = getString(R.string.crime_report_no_suspect)
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect)
        }

        return getString(R.string.crime_report, mCrime.title, dateString, solvedString, suspect)
    }

    fun updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile!!.exists()) {
            crime_photo.setImageDrawable(null)
        } else {
            val bitmap = getScaledBitmap(mPhotoFile!!.path, activity)
            crime_photo.setImageBitmap(bitmap)
        }
    }
}