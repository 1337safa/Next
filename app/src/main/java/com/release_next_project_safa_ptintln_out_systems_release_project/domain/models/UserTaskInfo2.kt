package com.release_next_project_safa_ptintln_out_systems_release_project.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlConstantInfo

@Entity(tableName = SqlConstantInfo.TABLE_NAME)
data class UserTaskInfo2(

    @PrimaryKey(autoGenerate = true)
    val userTaskInfoID: Int? = null,

    @ColumnInfo(name = SqlConstantInfo.COLUMN_NAME_USER_IS_COMPLETE_TASK)
    val userIsCompleteTask: Boolean,

    @ColumnInfo(name = SqlConstantInfo.COLUMN_NAME_USER_TASK)
    val userTask: String?

)