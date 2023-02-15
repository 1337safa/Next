package com.release_next_project_safa_ptintln_out_systems_release_project.domain.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlConstantInfo

@Entity(tableName = SqlConstantInfo.TABLE_NAME)
data class UserTaskInfo(

    @PrimaryKey(autoGenerate = true)
    val userTaskInfoID: Int? = null,

    @ColumnInfo(name = SqlConstantInfo.COLUMN_NAME_USER_IS_COMPLETE_TASK)
    var userIsCompleteTask: Boolean,

    @ColumnInfo(name = SqlConstantInfo.COLUMN_NAME_USER_TASK)
    var userTask: String?

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(userTaskInfoID)
        parcel.writeByte(if (userIsCompleteTask) 1 else 0)
        parcel.writeString(userTask)
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