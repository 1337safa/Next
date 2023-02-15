package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces

import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo

interface UserInteractsWithTasks {

    fun userClickOnCheckBoxFromUserTaskInfoRcView(userTaskInfo: UserTaskInfo, isChecked: Boolean)
    fun removeUserTaskByIdInSqlFromUserTaskInfoRcView(ID: Int)
    fun userLongClickOnUserTaskInUserTaskInfoRcView(userTask: UserTaskInfo)

}