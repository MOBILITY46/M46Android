package se.mobility46.tabbar

import android.os.Parcel
import android.os.Parcelable

data class TabBarConfig(val swipeable: Boolean, val indicatorColor: Int) : Parcelable {


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(if (swipeable) 1 else 0)
        parcel.writeInt(indicatorColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TabBarConfig> {
        override fun createFromParcel(parcel: Parcel): TabBarConfig {
            return TabBarConfig(
                parcel.readInt() == 1,
                parcel.readInt()
            )
        }

        override fun newArray(size: Int): Array<TabBarConfig?> {
            return arrayOfNulls(size)
        }
    }

}