package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.sql_dao.UserTaskInfoDao
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo2

@Database(entities = [UserTaskInfo2::class], version = 1)
abstract class SqlManager2(): RoomDatabase() {

    abstract fun getUserTaskInfoDao(): UserTaskInfoDao

    companion object {

        fun getSqlDatabase(context: Context): SqlManager2 {
            return Room.databaseBuilder(
                context, SqlManager2::class.java, SqlConstantInfo.DATABASE_NAME
            ).build()
        }

    }

}