package com.example.plan_me.data.remote.dto.category

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class AllCategoryRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: CategoryListResponse
)

data class CategoryListResponse(
    @SerializedName("categoryList") val categoryList: List<CategoryList>
)
data class CategoryList(
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("name") var name: String,
    @SerializedName("emoticon") var emoticon: String,
    @SerializedName("color") var color: Int,
    @SerializedName("meStoryHidden") val meStoryHidden: Boolean,
    @SerializedName("createdAt") val createdAt: String, // 날짜와 시간에 대한 포맷을 맞춰서 사용
    @SerializedName("updatedAt") val updatedAt: String // 날짜와 시간에 대한 포맷을 맞춰서 사용
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(categoryId)
        parcel.writeString(name)
        parcel.writeString(emoticon)
        parcel.writeInt(color)
        parcel.writeByte(if (meStoryHidden) 1 else 0)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryList> {
        override fun createFromParcel(parcel: Parcel): CategoryList {
            return CategoryList(parcel)
        }

        override fun newArray(size: Int): Array<CategoryList?> {
            return arrayOfNulls(size)
        }
    }

}