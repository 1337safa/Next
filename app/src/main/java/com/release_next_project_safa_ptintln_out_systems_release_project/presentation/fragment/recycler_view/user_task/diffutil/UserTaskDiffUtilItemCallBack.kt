package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo

class UserTaskDiffUtilItemCallBack(): DiffUtil.ItemCallback<UserTaskInfo>() {

    override fun areItemsTheSame(oldItem: UserTaskInfo, newItem: UserTaskInfo): Boolean {
        return oldItem.userTaskID == newItem.userTaskID
    }

    override fun areContentsTheSame(oldItem: UserTaskInfo, newItem: UserTaskInfo): Boolean {
        return oldItem.equals(newItem)
    }


}