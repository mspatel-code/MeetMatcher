package ca.group6.meetmatcher.model
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
class Meeting(var date: String?, var time: String?, var location: String?): Parcelable {

    private constructor(parcel: Parcel) : this(
        date = parcel.readString(),
        time = parcel.readString(),
        location = parcel.readString()
    )

    companion object : Parceler<Meeting> {
        override fun Meeting.write(parcel: Parcel, flags: Int) {
            parcel.writeString(date)
            parcel.writeString(time)
            parcel.writeString(location)
        }

        override fun create(parcel: Parcel): Meeting = Meeting(parcel.readString(), parcel.readString(), parcel.readString())
    }

    override fun toString(): String {
        return "$date: $time "
    }
}