package ca.group6.meetmatcher
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Meeting(val date: String?, val time: String?, val location: String?): Parcelable {
////    val date = inpDate
////    val time = inpTime
////    val location = inpLocation
//
//    private constructor(parcel: Parcel) : this(
//        date = parcel.readString(),
//        time = parcel.readString(),
//        location = parcel.readString()
//    )
//
//    override fun writeToParcel(parcel: Parcel?, flags: Int) {
//        parcel?.writeString(date)
//        parcel?.writeString(time)
//        parcel?.writeString(location)
//    }
}