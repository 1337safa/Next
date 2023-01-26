package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.usecase.*
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.UserClickOnTaskCheckBox
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.UserInteractsWithTasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainContentViewModel(

    private val communicateWithLocalSqlDatabaseUseCase: CommunicateWithLocalSqlDatabaseUseCase

) : ViewModel(), UserInteractsWithTasks, UserClickOnTaskCheckBox {

    private val userTasksMutableLiveData: MutableLiveData<ArrayList<UserTaskInfo>> =
        MutableLiveData<ArrayList<UserTaskInfo>>()
    val userTasksLiveData: LiveData<ArrayList<UserTaskInfo>> = userTasksMutableLiveData

    init {

        try {

            communicateWithLocalSqlDatabaseUseCase.openDb()
            /*
            I don't use coroutines here because when I run the application
            for the first time, the local database doesn't have time to open.
            */

        } catch (e: Exception) {
            Log.e("MyLog", "MainContentViewModel openDb exception: $e")
        }

    }

    fun insertUserTaskToSql(userTask: UserTaskInfo) {

        viewModelScope.launch(Dispatchers.IO) {

            val doInsertToDb = async {

                communicateWithLocalSqlDatabaseUseCase.insertUserTaskToSql(userTask)

            }

            doInsertToDb.await()

        }

    }

    fun getUserTasks() {

        viewModelScope.launch(Dispatchers.IO) {

            val doGetUserTasks = async {

                this@MainContentViewModel.userTasksMutableLiveData.postValue(

                    communicateWithLocalSqlDatabaseUseCase.getUserTasks()

                )

            }

            doGetUserTasks.await()

        }

    }

    fun deleteUserTaskByIdInSql(ID: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val doDeleteUserTaskInfo = async {

                communicateWithLocalSqlDatabaseUseCase.deleteUserTaskByIdInSql(ID)

            }

            doDeleteUserTaskInfo.await()

        }

    }

    fun updateUserTaskInfo(userTask: UserTaskInfo) {

        viewModelScope.launch(Dispatchers.IO) {

            val doUpdateUserTaskInfo = async {

                communicateWithLocalSqlDatabaseUseCase.updateUserTask(userTask)

            }

            doUpdateUserTaskInfo.await()

        }

    }

    override fun removeUserTaskByIdInSql(ID: String) {

        deleteUserTaskByIdInSql(ID)

    }

    override fun updateUserTaskInSqlIsUserCompleteTaskOrNot(userTask: UserTaskInfo) {

        updateUserTaskInfo(userTask)

    }

    override fun onCleared() {

        try {

            viewModelScope.launch(Dispatchers.IO) {

                val doCloseDb = async {
                    communicateWithLocalSqlDatabaseUseCase.closeDb()
                }

                doCloseDb.await()

            }

        } catch (e: Exception) {
            Log.e("MyLog", "mainContentViewModel closeDb exception: $e")
        }


        super.onCleared()

    }

}