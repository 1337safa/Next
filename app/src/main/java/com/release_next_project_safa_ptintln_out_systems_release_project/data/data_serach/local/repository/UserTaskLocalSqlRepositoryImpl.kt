package com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.repository

import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlManager
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.repository.UserTaskLocalSqlRepository

class UserTaskLocalSqlRepositoryImpl(

    private val sqlManager: SqlManager

): UserTaskLocalSqlRepository {

    override fun openDb() {

        sqlManager.openDb()

    }

    override fun insertUserTaskToSql(userTask: UserTaskInfo) {

        sqlManager.insertUserTask(userTask)

    }

    override fun getUserTasks(): ArrayList<UserTaskInfo> {

        return sqlManager.getUserTasks()

    }

    override fun updateUserTask(userTask: UserTaskInfo) {

        sqlManager.updateUserTask(userTask)

    }

    override fun deleteUserTaskByIdInSql(ID: String) {

        sqlManager.deleteUserTaskByIdInSql(ID)

    }

    override fun closeDb() {

        sqlManager.closeDb()

    }

}