package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlManager
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainContentViewModel(
    sqlManager: SqlManager
) : ViewModel() {

    private val userTaskInfoDao = sqlManager.getUserTaskInfoDao()

    fun insertUserTaskToSql(userTaskInfo: UserTaskInfo) {

        try {
            viewModelScope.launch(Dispatchers.IO) {
                val doInsertToDb = async {
                    userTaskInfoDao.insertUserTaskInfo(userTaskInfo = userTaskInfo)
                }
                doInsertToDb.await()
            }
        } catch (e: Exception) {
            throw e
        }

    }

    fun getUserTasks(): Flow<List<UserTaskInfo>> {
        return userTaskInfoDao.getUserTasks()
    }

    fun removeUserTaskByIdInSql(ID: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                userTaskInfoDao.removeById(ID)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun updateUserTaskInfo(userTaskInfo: UserTaskInfo) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                userTaskInfoDao.updateUserTaskInfoById(
                    userTaskInfo
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}