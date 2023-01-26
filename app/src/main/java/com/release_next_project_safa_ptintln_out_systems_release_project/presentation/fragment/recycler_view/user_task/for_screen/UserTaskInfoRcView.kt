package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.for_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.release_next_project_safa_ptintln_out_systems_release_project.databinding.UserTaskExampleBinding
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import androidx.recyclerview.widget.ListAdapter
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.diffutil.UserTaskDiffUtilItemCallBack
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.*

class UserTaskInfoRcView(

    private val userInteractsWithTasks: UserInteractsWithTasks,
    private val userClickOnTaskCheckBox: UserClickOnTaskCheckBox,
    private val userLongClickOnUserTask: UserLongClickOnUserTask,
    private val reportUserActionsInUserTaskInRcView: ReportUserActionsInUserTaskInRcView

) : ListAdapter<UserTaskInfo, UserTaskInfoRcView.ContentHolder>(UserTaskDiffUtilItemCallBack()),
    UserDeleteTask {

    class ContentHolder(
        val binding: UserTaskExampleBinding,
        private val userInteractsWithTasks: UserInteractsWithTasks,
        private val userClickOnTaskCheckBox: UserClickOnTaskCheckBox,
        private val userLongClickOnUserTask: UserLongClickOnUserTask
        ):
        RecyclerView.ViewHolder(binding.root) {

            fun bindUserTaskInfo(userTaskInfo: UserTaskInfo) {

                binding.userTask.text = userTaskInfo.userTask
                binding.userCheckBox.isChecked = userTaskInfo.userIsCompleteTask

                longClickOnItemViewListener(
                    binding.root,
                    userTaskInfo
                )

                binding.userCheckBox.setOnCheckedChangeListener(object :
                    CompoundButton.OnCheckedChangeListener {

                    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

                        userTaskInfo.userIsCompleteTask = isChecked

                        userClickOnTaskCheckBox.updateUserTaskInSqlIsUserCompleteTaskOrNot(
                            userTaskInfo
                        )

                    }

                })

            }

        private fun longClickOnItemViewListener(itemView: View, item: UserTaskInfo) {

            itemView.setOnLongClickListener(object : View.OnLongClickListener {

                override fun onLongClick(v: View?): Boolean {

                    userLongClickOnUserTask.userLongClickOnUserTask(item)

                    return true

                }

            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = UserTaskExampleBinding.inflate(inflater, parent, false)

        return ContentHolder(
            binding, this.userInteractsWithTasks, this.userClickOnTaskCheckBox, this.userLongClickOnUserTask
        )

    }

    override fun onBindViewHolder(viewHolder: ContentHolder, position: Int) {

        viewHolder.bindUserTaskInfo(this.getItem(viewHolder.absoluteAdapterPosition))

    }

    override fun removeByIdFromSql(position: Int) {

        if (position != null) {

            /*
            * Checking if something goes wrong and suddenly comes null
            */

            reportUserActionsInUserTaskInRcView.goingToDeleteElement()

            userInteractsWithTasks.removeUserTaskByIdInSql(
                this.getItem(position).userTaskID!!
            )

            val current = this.currentList.toMutableList()
            current.removeAt(position)
            this@UserTaskInfoRcView.submitList(current)

            reportUserActionsInUserTaskInRcView.removedElement()

        }

    }

    fun getAllUserTasksInfoSize(): Int = this.currentList.size

}