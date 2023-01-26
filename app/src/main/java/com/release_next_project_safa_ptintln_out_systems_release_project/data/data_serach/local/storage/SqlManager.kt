package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.helper.InteractionWithLocalDatabaseSqlForDelete
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.helper.InteractionWithLocalDatabaseSqlForInsert
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.helper.InteractionWithLocalDatabaseSqlForRead
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.helper.InteractionWithLocalDatabaseSqlForUpdate
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo

class SqlManager(context: Context) {

    private val sqlDatabaseHelper = SqlDatabaseHelper(context)
    private var db: SQLiteDatabase? = null
    private var interactionWithLocalDatabaseSqlForInsert:
            InteractionWithLocalDatabaseSqlForInsert? = null
    private var interactionWithLocalDatabaseSqlForRead:
            InteractionWithLocalDatabaseSqlForRead? = null
    private var interactionWithLocalDatabaseSqlForDelete:
            InteractionWithLocalDatabaseSqlForDelete? = null
    private var interactionWithLocalDatabaseSqlForUpdate:
            InteractionWithLocalDatabaseSqlForUpdate? = null

    fun openDb() {

        db = sqlDatabaseHelper.writableDatabase

        if (db != null) {
            interactionWithLocalDatabaseSqlForInsert =
                InteractionWithLocalDatabaseSqlForInsert(db!!)
            interactionWithLocalDatabaseSqlForRead = InteractionWithLocalDatabaseSqlForRead()
            interactionWithLocalDatabaseSqlForDelete =
                InteractionWithLocalDatabaseSqlForDelete(db!!)
            interactionWithLocalDatabaseSqlForUpdate =
                InteractionWithLocalDatabaseSqlForUpdate(db!!)
        }

    }

    fun insertUserTask(userTaskInfo: UserTaskInfo) {

        if (db != null && interactionWithLocalDatabaseSqlForInsert != null) {

            interactionWithLocalDatabaseSqlForInsert!!.insertNewUserTaskInfo(
                SqlConstantInfo.TABLE_NAME, SqlConstantInfo.COLUMN_NAME_USER_TASK,
                SqlConstantInfo.COLUMN_NAME_USER_IS_COMPLETE_TASK, userTaskInfo, null
            )

        }

    }

    fun getUserTasks(): ArrayList<UserTaskInfo> {

        val tasksList = ArrayList<UserTaskInfo>()

        val cursor: Cursor? = db?.query(
            SqlConstantInfo.TABLE_NAME,
            null, null, null, null, null, null
        )

        if (cursor != null && interactionWithLocalDatabaseSqlForRead != null) {

            while (cursor.moveToNext()) {

                try {

                    val userTaskFromDb: String = interactionWithLocalDatabaseSqlForRead!!
                        .getStringFromSqlDbByColumnIndex(
                            cursor,
                            cursor.getColumnIndexOrThrow(SqlConstantInfo.COLUMN_NAME_USER_TASK)
                        )

                    val userIsCompleteTask: Boolean = interactionWithLocalDatabaseSqlForRead!!
                        .getBooleanFromSqlDbByColumnIndex(
                            cursor,
                            cursor.getColumnIndexOrThrow(SqlConstantInfo.COLUMN_NAME_USER_IS_COMPLETE_TASK)
                        )

                    val taskId = interactionWithLocalDatabaseSqlForRead!!
                        .getStringFromSqlDbByColumnIndex(
                            cursor,
                            cursor.getColumnIndexOrThrow(SqlConstantInfo.COLUMN_NAME_USER_TASK_ID)
                        )

                    val userTaskInfo: UserTaskInfo =
                        getConvertedUserTaskInfo(userTaskFromDb, userIsCompleteTask, taskId)

                    tasksList.add(userTaskInfo)

                } catch (e: Exception) {
                    Log.e("MyLog", "getUserTasksSql: $e")
                }

            }

        }

        cursor?.close()
        return tasksList

    }

    fun deleteUserTaskByIdInSql(ID: String) {

        if (db != null && interactionWithLocalDatabaseSqlForDelete != null) {

            interactionWithLocalDatabaseSqlForDelete!!
                .deleteUserTasksInfoFromDbSqlByID(
                    SqlConstantInfo.TABLE_NAME,
                    SqlConstantInfo.COLUMN_NAME_USER_TASK_ID + "=?", ID
                )

        }

    }

    fun updateUserTask(userTaskInfo: UserTaskInfo) {

        if (db != null && interactionWithLocalDatabaseSqlForUpdate != null) {

            interactionWithLocalDatabaseSqlForUpdate!!
                .updateUserTaskInfoInLocalDatabaseSqlById(
                    SqlConstantInfo.TABLE_NAME, userTaskInfo,
                    SqlConstantInfo.COLUMN_NAME_USER_TASK_ID + "=?"
                )

        }

    }

    private fun getConvertedUserTaskInfo(
        userTaskFromDb: String,
        userIsCompleteTask: Boolean,
        taskId: String
    ): UserTaskInfo {
        return UserTaskInfo(userTaskFromDb, userIsCompleteTask, taskId)
    }

    fun closeDb() {

        db?.close()

    }

}