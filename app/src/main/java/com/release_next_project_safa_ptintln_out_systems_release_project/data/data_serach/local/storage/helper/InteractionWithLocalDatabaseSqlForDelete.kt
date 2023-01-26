package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.helper

import android.database.sqlite.SQLiteDatabase
import android.util.Log

class InteractionWithLocalDatabaseSqlForDelete(

    private val db: SQLiteDatabase

) {

    fun deleteUserTasksInfoFromDbSqlByID(
        tableName: String, whereClause: String, ID: String) {

        try {

            db.delete(
                tableName, whereClause, arrayOf(ID)
            )

        } catch (e: Exception) {
            Log.e("MyLog", "deleteUserTasksInfoFromDbSqlByID" +
                    "(InteractionWithLocalDatabaseSqlForDelete) " +
                "exception: $e")
        }

    }

}