package com.release_next_project_safa_ptintln_out_systems_release_project.domain.repository

import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo

interface UserTaskLocalSqlRepository {

    fun openDb()
    fun insertUserTaskToSql(userTask: UserTaskInfo)
    fun getUserTasks(): ArrayList<UserTaskInfo>
    fun updateUserTask(userTask: UserTaskInfo)
    fun deleteUserTaskByIdInSql(ID: String)
    fun closeDb()

}