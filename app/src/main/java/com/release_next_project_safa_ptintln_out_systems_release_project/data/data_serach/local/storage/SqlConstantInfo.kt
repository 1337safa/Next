package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage

object SqlConstantInfo {

    const val DATABASE_NAME = "next_database_sql.db"
    const val DATABASE_VERSION = 1

    const val TABLE_NAME = "next_table"
    const val COLUMN_NAME_USER_TASK = "user_task_column"
    const val COLUMN_NAME_USER_IS_COMPLETE_TASK = "user_complete_task"
    const val COLUMN_NAME_USER_TASK_ID = "_id"

    const val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (" +
            "$COLUMN_NAME_USER_TASK_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_USER_TASK TEXT," +
            "$COLUMN_NAME_USER_IS_COMPLETE_TASK TEXT)"

    const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

}