package ru.bupyc9.criminalintent.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CrimeBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        @JvmStatic val VERSION = 1
        @JvmStatic val DATABASE_NAME = "crimeBase.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
                "create table ${CrimeDbSchema.CrimeTable.NAME} (" +
                        "${CrimeDbSchema.CrimeTable.Cols.ID} integer primary key autoincrement, " +
                        "${CrimeDbSchema.CrimeTable.Cols.TITLE}," +
                        "${CrimeDbSchema.CrimeTable.Cols.DATE}," +
                        "${CrimeDbSchema.CrimeTable.Cols.SOLVED}," +
                        "${CrimeDbSchema.CrimeTable.Cols.SUSPECT}" +
                        ")"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}