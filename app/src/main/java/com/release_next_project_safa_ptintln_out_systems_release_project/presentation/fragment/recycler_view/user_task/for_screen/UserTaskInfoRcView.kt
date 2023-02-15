package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.for_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.release_next_project_safa_ptintln_out_systems_release_project.databinding.UserTaskExampleBinding
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import androidx.recyclerview.widget.ListAdapter
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.diffutil.UserTaskDiffUtilItemCallBack
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.*

class UserTaskInfoRcView(
    private val userInteractsWithTasks: UserInteractsWithTasks
) : ListAdapter<UserTaskInfo, UserTaskInfoRcView.ContentHolder>(UserTaskDiffUtilItemCallBack()),
    UserRemoveTaskBySwipe {

    class ContentHolder(
        val binding: UserTaskExampleBinding
    ) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = UserTaskExampleBinding.inflate(inflater, parent, false)

        return ContentHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ContentHolder, position: Int) {
        viewHolder.binding.userTask.text = getItem(position).userTask
        viewHolder.binding.userCheckBox.isChecked = getItem(position).userIsCompleteTask
        longClickOnItemViewListener(viewHolder.binding.root, getItem(position))
        viewHolder.binding.userCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            userInteractsWithTasks.userClickOnCheckBoxFromUserTaskInfoRcView(
                getItem(position), isChecked
            )
        }
    }

    private fun longClickOnItemViewListener(itemView: View, item: UserTaskInfo) {
        itemView.setOnLongClickListener {
            userInteractsWithTasks.userLongClickOnUserTaskInUserTaskInfoRcView(item)
            return@setOnLongClickListener true
        }
    }

    fun getAllUserTasksInfoSize(): Int = this.currentList.size

    override fun removeByIdFromSql(position: Int) {
        getItem(position).userTaskInfoID?.let {
            userInteractsWithTasks.removeUserTaskByIdInSqlFromUserTaskInfoRcView(it)
        }
        val current = currentList.toMutableList()
        current.removeAt(position)
        current.addAll(current)
    }

}