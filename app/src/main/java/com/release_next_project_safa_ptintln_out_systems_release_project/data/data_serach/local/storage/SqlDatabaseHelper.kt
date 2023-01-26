package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlDatabaseHelper(

    context: Context

): SQLiteOpenHelper(
    context, SqlConstantInfo.DATABASE_NAME, null, SqlConstantInfo.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SqlConstantInfo.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SqlConstantInfo.SQL_DROP_TABLE)
        onCreate(db)
    }

}