package ru.bupyc9.criminalintent.ui

import ru.bupyc9.criminalintent.models.Crime

class CrimeLab private constructor() {
    val crimes: MutableList<Crime> = mutableListOf()

    private object Holder {
        val INSTANCE = CrimeLab()
    }

    companion object {
        val instance: CrimeLab by lazy { Holder.INSTANCE }
    }
}