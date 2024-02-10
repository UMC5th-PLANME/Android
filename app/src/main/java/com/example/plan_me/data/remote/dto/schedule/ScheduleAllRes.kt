package com.example.plan_me.data.remote.dto.schedule

import android.os.Parcel
import android.os.Parcelable
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class AllScheduleRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ScheduleListResponse
)

data class ScheduleListResponse(
    @SerializedName("scheduleList") val scheduleList: List<ScheduleList>
)

data class ScheduleList(
    @SerializedName("id") val id: Int,
    @SerializedName("status") var status: Boolean,
    @SerializedName("category_id") val category_id: Int,
    @SerializedName("repeat_period") val repeat_period: String,
    @SerializedName("title") val title: String,
    @SerializedName("start_time") val start_time: String,
    @SerializedName("end_time") val end_time: String,
    @SerializedName("alarm") val alarm: Boolean,
    @SerializedName("alarm_time") val alarm_time: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (status) 1 else 0)
        parcel.writeInt(category_id)
        parcel.writeString(repeat_period)
        parcel.writeString(title)
        parcel.writeString(start_time)
        parcel.writeString(end_time)
        parcel.writeByte(if (alarm) 1 else 0)
        parcel.writeString(alarm_time)
        parcel.writeString(created_at)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduleList> {
        override fun createFromParcel(parcel: Parcel): ScheduleList {
            return ScheduleList(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleList?> {
            return arrayOfNulls(size)
        }
    }
}