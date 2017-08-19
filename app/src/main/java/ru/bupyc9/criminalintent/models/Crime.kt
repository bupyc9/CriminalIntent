package ru.bupyc9.criminalintent.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Crime(
        var id: Int,
        var title: String,
        var date: Date,
        var solved: Boolean,
        var suspect: String
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Crime> = object : Parcelable.Creator<Crime> {
            override fun createFromParcel(source: Parcel): Crime = Crime(source)
            override fun newArray(size: Int): Array<Crime?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readSerializable() as Date,
            1 == source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeSerializable(date)
        dest.writeInt((if (solved) 1 else 0))
        dest.writeString(suspect)
    }
}