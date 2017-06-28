package ru.bupyc9.criminalintent.models

import java.util.*

data class Crime(
        var id: Int,
        var title: String,
        var date: Date,
        var solved: Boolean
) {
}