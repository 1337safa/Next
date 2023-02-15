package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.sql_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlConstantInfo
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserTaskInfoDao {

    @Insert
    fun insertUserTaskInfo(userTaskInfo: UserTaskInfo)

    @Query("SELECT * FROM ${SqlConstantInfo.TABLE_NAME}")
    fun getUserTasks(): Flow<List<UserTaskInfo>>

    @Query("UPDATE ${SqlConstantInfo.TABLE_NAME} SET ${SqlConstantInfo.COLUMN_NAME_USER_TASK}" +
            " = :userTask, ${SqlConstantInfo.COLUMN_NAME_USER_IS_COMPLETE_TASK} = :isCompleted" +
            " WHERE userTaskInfoID = :ID")
    fun updateUserTaskInfoById(ID: Int?, userTask: String?, isCompleted: Boolean)

    @Update
    fun updateUserTaskInfoById(userTaskInfo: UserTaskInfo)

    @Query("DELETE FROM ${SqlConstantInfo.TABLE_NAME} WHERE userTaskInfoID = :ID")
    fun removeById(ID: Int?)

}