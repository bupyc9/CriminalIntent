package ru.bupyc9.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ru.bupyc9.criminalintent.ui.crimelist.CrimeListFragment

class CrimeActivity : AppCompatActivity() {
    private val FRAGMENT_TAG = "fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        if (savedInstanceState == null) {
            addFragment(CrimeListFragment.newInstance())
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val show = supportFragmentManager.backStackEntryCount > 0
            supportActionBar?.setHomeButtonEnabled(show)
            supportActionBar?.setDisplayHomeAsUpEnabled(show)
        }
    }

    fun addFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment, FRAGMENT_TAG)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            supportFragmentManager.popBackStack()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
