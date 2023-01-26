package com.release_next_project_safa_ptintln_out_systems_release_project.domain.usecase

import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.repository.UserTaskLocalSqlRepository

class CommunicateWithLocalSqlDatabaseUseCase(

    private val userTaskLocalSqlRepository: UserTaskLocalSqlRepository

) {

    fun openDb() {

        userTaskLocalSqlRepository.openDb()

    }

    fun deleteUserTaskByIdInSql(ID: String) {

        userTaskLocalSqlRepository.deleteUserTaskByIdInSql(ID)

    }

    fun getUserTasks(): ArrayList<UserTaskInfo> {

        return userTaskLocalSqlRepository.getUserTasks()

    }

    fun insertUserTaskToSql(userTask: UserTaskInfo) {

        userTaskLocalSqlRepository.insertUserTaskToSql(userTask)

    }

    fun updateUserTask(userTask: UserTaskInfo) {

        userTaskLocalSqlRepository.updateUserTask(userTask)

    }

    fun closeDb() {

        userTaskLocalSqlRepository.closeDb()

    }

}