package ru.bupyc9.criminalintent.db

class CrimeDbSchema {
    class CrimeTable {
        companion object {
            @JvmStatic val NAME = "crimes"
        }
        class Cols {
            companion object {
                @JvmStatic val ID = "id"
                @JvmStatic val TITLE = "title"
                @JvmStatic val DATE = "date"
                @JvmStatic val SOLVED = "solved"
            }
        }
    }
}