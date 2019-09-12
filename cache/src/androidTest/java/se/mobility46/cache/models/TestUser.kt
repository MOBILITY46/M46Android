package se.mobility46.cache.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject


class TestUser(val firstName: String, val lastName: String) : Parcelable {
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

interface JsonDecoder<out T> {
    fun decode(s: String): T
}

interface JsonEncoder<T> {
    fun encode(o: TestUser): String
}

class TestUserDecoder: JsonDecoder<TestUser> {
    override fun decode(value: String): TestUser {
        val s = JSONObject(value)
        return TestUser(
            s.getString("firstName"),
            s.getString("lastName")
        )
    }
}

class TestUserEncoder: JsonEncoder<TestUser> {
    override fun encode(o: TestUser): String {
        val json = JSONObject()
        json.put("firstName", o.firstName)
        json.put("lastName", o.lastName)
        return json.toString()
    }
}
