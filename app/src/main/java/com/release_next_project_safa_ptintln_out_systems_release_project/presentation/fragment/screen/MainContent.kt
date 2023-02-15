package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.screen

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.release_next_project_safa_ptintln_out_systems_release_project.R
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.for_screen.item_touhc.ItemTouchHelperSwipe
import com.release_next_project_safa_ptintln_out_systems_release_project.databinding.FragmentMainContentBinding
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.for_screen.UserTaskInfoRcView
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.ReportUserActionsInUserTaskInRcView
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.UserInteractsWithTasks
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.viewmodel.MainContentViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SAVE_USER_TASK_BUNDLE = "save_user_task_bundle"
const val MAX_COUNT_OF_TASK = 100

class MainContent : Fragment(), ReportUserActionsInUserTaskInRcView, UserInteractsWithTasks {

    private var _binding: FragmentMainContentBinding? = null
    private val binding get() = _binding!!
    /*  :)  */

    private val mainContentViewModel by viewModel<MainContentViewModel>()

    private val navHostController: NavController by lazy {
        NavHostFragment.findNavController(this)
    }

    private val userTaskRcView: UserTaskInfoRcView by lazy {
        UserTaskInfoRcView(this)
    }

    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(
            ItemTouchHelperSwipe(
                userTaskRcView
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainContentBinding.inflate(inflater, container, false)

        if (savedInstanceState != null) {
            init(savedInstanceState)
        }
        listenerOnEachFromSqlDatabase()

        return binding.root
    }

    private fun listenerOnEachFromSqlDatabase() {
        lifecycleScope.launchWhenStarted {
            mainContentViewModel.getUserTasks().onEach {
                userTaskRcView.submitList(it)
                hideProgressBar(binding.mainProgressBar)
            }.collect()
        }
    }

    private fun init(savedInstanceState: Bundle) {
        showProgressBar(binding.mainProgressBar)
        val userText = savedInstanceState.getString(SAVE_USER_TASK_BUNDLE)
        if (userText != null) {
            binding.edtvUserNewTask.setText(userText)
        }
    }

    private fun ifDataExistShowDataInEditTextUserNewTask(savedInstanceState: Bundle?) {
        val userTasks =
            savedInstanceState?.getString(SAVE_USER_TASK_BUNDLE)
        if (userTasks != null) {
            binding.edtvUserNewTask.setText(userTasks)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding.rcUserTask)
        binding.rcUserTask.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.VERTICAL, false
        )

        binding.rcUserTask.adapter = userTaskRcView
        binding.rcUserTask.setItemViewCacheSize(10)

        edTvUserNewTaskActionListener()
        parentFragmentManagerFragmentResultListener()

    }

    private fun parentFragmentManagerFragmentResultListener() {

        parentFragmentManager.setFragmentResultListener(

            UserChangesTask.USER_TASK_REQUEST_KEY, viewLifecycleOwner,
            object : FragmentResultListener {
                override fun onFragmentResult(requestKey: String, result: Bundle) {

                    if (requestKey == UserChangesTask.USER_TASK_REQUEST_KEY) {

                        showProgressBar(binding.mainProgressBar)

                        val userTask = result
                            .getParcelable<UserTaskInfo>(UserChangesTask.USER_TASK_KEY)

                        if (userTask != null) {

                            updateUserTaskInfoInDatabaseSql(userTask)
                            getUserTasks()

                        }

                    }

                }
            }

        )

    }

    private fun updateUserTaskInfoInDatabaseSql(userTask: UserTaskInfo) {
        mainContentViewModel.updateUserTaskInfo(userTask)
    }

    private fun edTvUserNewTaskActionListener() {

        binding.edtvUserNewTask.setOnEditorActionListener(
            object : TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    showProgressBar(binding.mainProgressBar)

                    if (userTaskRcView.getAllUserTasksInfoSize() <= MAX_COUNT_OF_TASK) {
                        val userTask = binding.edtvUserNewTask.text.toString()
                        val newUserTaskInfo = UserTaskInfo(
                            userTaskInfoID = null, userIsCompleteTask = false, userTask = userTask
                        )
                        mainContentViewModel.insertUserTaskToSql(newUserTaskInfo)
                        hideInputMethod()
                        clearEditTextView(binding.edtvUserNewTask)
                    } else {
                        Toast.makeText(requireContext(), resources.getString(R.string.exceeding_the_limit_of_tasks),
                        Toast.LENGTH_LONG).show()
                    }

                    hideProgressBar(binding.mainProgressBar)
                    handled = true
                }

                return handled
            }

        })

    }

    private fun getUserTasks() {
        mainContentViewModel.getUserTasks()
    }

    private fun clearEditTextView(edtv: EditText) {
        edtv.setText("")
        edtv.clearFocus()
    }

    private fun hideInputMethod() {
        val imm =
            activity?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val userTask = binding.edtvUserNewTask.text.toString()
        outState.putString(SAVE_USER_TASK_BUNDLE, userTask)
    }

    override fun goingToDeleteElementInUserTaskInfoRcView() {
        showProgressBar(binding.mainProgressBar)
    }

    override fun removedElementInUserTaskInfoRcView() {
        hideProgressBar(binding.mainProgressBar)
    }

    override fun userClickOnCheckBoxFromUserTaskInfoRcView(
        userTaskInfo: UserTaskInfo, isChecked: Boolean
    ) {
        mainContentViewModel.updateUserTaskInfo(
            userTaskInfo = userTaskInfo
        )
    }

    override fun removeUserTaskByIdInSqlFromUserTaskInfoRcView(ID: Int) {
        mainContentViewModel.removeUserTaskByIdInSql(ID)
     }

    override fun userLongClickOnUserTaskInUserTaskInfoRcView(userTask: UserTaskInfo) {
        navHostController.navigate(
            R.id.action_main_content_to_user_changes_task,
            bundleOf(UserChangesTask.USER_TASK_KEY to userTask)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainContent()
    }

}