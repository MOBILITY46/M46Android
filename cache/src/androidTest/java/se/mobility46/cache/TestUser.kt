package se.mobility46.cache

import android.os.Parcel
import android.os.Parcelable

data class TestUser(val firstName: String, val lastName: String) : Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(firstName)
        dest?.writeString(lastName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestUser> {

        override fun createFromParcel(parcel: Parcel): TestUser {
            return TestUser(
                firstName = parcel.readString() ?: "",
                lastName = parcel.readString() ?: ""
            )
        }

        override fun newArray(size: Int): Array<TestUser?> {
            return arrayOfNulls(size)
        }
    }
}