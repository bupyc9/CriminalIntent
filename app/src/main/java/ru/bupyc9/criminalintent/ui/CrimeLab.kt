package ru.bupyc9.criminalintent.ui

import android.content.Context
import ru.bupyc9.criminalintent.models.Crime

class CrimeLab private constructor(var mContext: Context) {
    private val crimes: MutableList<Crime> = mutableListOf()

    companion object {
        @JvmStatic private var sCrimeLab: CrimeLab? = null

        @JvmStatic fun get(context: Context): CrimeLab {
            if (sCrimeLab == null) {
                sCrimeLab = CrimeLab(context)
            }

            return sCrimeLab!!
        }
    }

    fun addCrime(crime: Crime) {
        crimes.add(crime)
    }

    fun getCrimes(): MutableList<Crime> {
        return crimes
    }

    fun getCrime(id: Int): Crime {
        return crimes.find { it.id == id }!!
    }
}