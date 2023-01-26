package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.helper

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import java.lang.Exception

class InteractionWithLocalDatabaseSqlForInsert(

    private val db: SQLiteDatabase

) {

    fun insertNewUserTaskInfo(
        tableName: String, columnNameUserTask: String, columnNameUserIsCompleteTask: String,
        userTaskInfo: UserTaskInfo, nullColumnHack: String?) {

        try {

            val contentValues = ContentValues()
            contentValues.put(columnNameUserTask, userTaskInfo.userTask)
            contentValues.put(columnNameUserIsCompleteTask, userTaskInfo.userIsCompleteTask)

            val newRow = db.insert(tableName, nullColumnHack, contentValues)
            Log.d("MyLog", "New row: $newRow")

        } catch (e: Exception) {
            Log.e("MyLog", "insertNewUserTaskInfo" +
                    "(InteractionWithLocalDatabaseSqlForInsert) " +
                    "exception: $e")
        }

    }

}