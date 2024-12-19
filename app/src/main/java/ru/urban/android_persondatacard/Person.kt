package ru.urban.android_persondatacard

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class Person (val name: String, val surname: String, val dateBirthday: LocalDate, val photoUri: String?)
    : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readSerializable() as LocalDate,
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeSerializable(dateBirthday)
        parcel.writeString(photoUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}