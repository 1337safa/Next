package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.helper

import android.database.Cursor
import android.util.Log

class InteractionWithLocalDatabaseSqlForRead() {

    fun getStringFromSqlDbByColumnIndex(cursor: Cursor, columnIndex: Int): String {

        var result: String = ""

        try {

            result = cursor.getString(columnIndex) ?: "Что-то пошло не так!"

        } catch (e: Exception) {
            Log.e("MyLog", "getStringByColumnIndex(InteractionWithLocalDatabaseSql) " +
                    "exception: $e")
        }

        return result

    }

    fun getBooleanFromSqlDbByColumnIndex(cursor: Cursor, columnIndex: Int): Boolean {

        var result: Boolean = false

        try {

            result = cursor.getString(columnIndex) == "1"

        } catch (e: Exception) {
            Log.e("MyLog", "getBooleanFromSqlDbByColumnIndex(InteractionWithLocalDatabaseSql) " +
                    "exception: $e")
        }

        return result

    }

}