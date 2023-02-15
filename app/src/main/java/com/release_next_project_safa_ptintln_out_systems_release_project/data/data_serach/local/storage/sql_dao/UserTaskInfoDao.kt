package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.sql_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlConstantInfo
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo2
import kotlinx.coroutines.flow.Flow

@Dao
interface UserTaskInfoDao {

    @Insert
    fun insertUserTaskInfo(userTaskInfo: UserTaskInfo2)

    @Query("SELECT * FROM ${SqlConstantInfo.TABLE_NAME}")
    fun getUserTasks(): Flow<List<UserTaskInfo2>>

}