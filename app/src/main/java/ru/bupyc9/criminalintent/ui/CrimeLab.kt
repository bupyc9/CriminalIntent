package ru.bupyc9.criminalintent.ui

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.bupyc9.criminalintent.db.CrimeBaseHelper
import ru.bupyc9.criminalintent.db.CrimeCursorWrapper
import ru.bupyc9.criminalintent.db.CrimeDbSchema
import ru.bupyc9.criminalintent.models.Crime

class CrimeLab private constructor(var mContext: Context) {
    private var mDatabase: SQLiteDatabase = CrimeBaseHelper(mContext).writableDatabase

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
        val values = getContentValues(crime)

        crime.id = mDatabase.insert(CrimeDbSchema.CrimeTable.NAME, null, values).toInt()
    }

    fun updateCrime(crime: Crime) {
        val values = getContentValues(crime)
        mDatabase.update(
                CrimeDbSchema.CrimeTable.NAME,
                values,
                "${CrimeDbSchema.CrimeTable.Cols.ID} = ?",
                arrayOf(crime.id.toString())
        )
    }

    fun getCrimes(): ArrayList<Crime> {
        val crimes = mutableListOf<Crime>()
        val cursor = queryCrimes(null, null)
        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                crimes.add(cursor.getCrime())
                cursor.moveToNext()
            }
        } finally {
            cursor.close()
        }

        return crimes as ArrayList<Crime>
    }

    fun getCrime(id: Int): Crime? {
        val cursor = queryCrimes(
                "${CrimeDbSchema.CrimeTable.Cols.ID} = ?",
                arrayOf(id.toString())
        )

        try {
            if (cursor.count == 0) {
                return null
            }

            cursor.moveToFirst()
            return cursor.getCrime()
        } finally {
            cursor.close()
        }
    }

    private fun getContentValues(crime: Crime): ContentValues {
        val values = ContentValues()

        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE, crime.title)
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE, crime.date.time)
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED, crime.solved.toString())

        return values
    }

    private fun queryCrimes(whereClause: String?, whereArgs: Array<String>?): CrimeCursorWrapper {
        val cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        )

        return CrimeCursorWrapper(cursor)
    }
}