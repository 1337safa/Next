package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.helper

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlConstantInfo
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo

class InteractionWithLocalDatabaseSqlForUpdate(

    private val db: SQLiteDatabase

) {

    fun updateUserTaskInfoInLocalDatabaseSqlById(
        tableName: String, userTaskInfo: UserTaskInfo, whereClause: String
    ) {

        val newContentValues = ContentValues()

        newContentValues.put(SqlConstantInfo.COLUMN_NAME_USER_TASK, userTaskInfo.userTask)

        newContentValues.put(
            SqlConstantInfo.COLUMN_NAME_USER_IS_COMPLETE_TASK,
            userTaskInfo.userIsCompleteTask
        )

        try {

            if (userTaskInfo.userTaskID != null) {
                db.update(tableName, newContentValues, whereClause, arrayOf(userTaskInfo.userTaskID))
            }

        } catch (e: Exception) {
            Log.d("MyLog", "updateUserTaskInfoInLocalDatabaseSqlById" +
                    "(InteractionWithLocalDatabaseSqlForUpdate) " +
                    "exception: $e")
        }

    }

}