package com.release_next_project_safa_ptintln_out_systems_release_project.domain.models

import android.os.Parcel
import android.os.Parcelable

data class UserTaskInfo(

    var userTask: String?,
    var userIsCompleteTask: Boolean,
    var userTaskID: String?

): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userTask)
        parcel.writeByte(if (userIsCompleteTask) 1 else 0)
        parcel.writeString(userTaskID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserTaskInfo> {
        override fun createFromParcel(parcel: Parcel): UserTaskInfo {
            return UserTaskInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserTaskInfo?> {
            return arrayOfNulls(size)
        }
    }

}