package ru.bupyc9.criminalintent.db

import android.database.Cursor
import android.database.CursorWrapper
import ru.bupyc9.criminalintent.models.Crime
import java.util.*

class CrimeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    fun getCrime() = Crime(
            getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.ID)).toInt(),
            getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE)),
            Date(getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE)).toLong()),
            getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED)).toBoolean(),
            getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SUSPECT))
    )
}