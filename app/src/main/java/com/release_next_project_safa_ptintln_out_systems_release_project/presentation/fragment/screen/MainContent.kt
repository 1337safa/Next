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
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.release_next_project_safa_ptintln_out_systems_release_project.R
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.for_screen.item_touhc.ItemTouchHelperSwipe
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlManager
import com.release_next_project_safa_ptintln_out_systems_release_project.databinding.FragmentMainContentBinding
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.UserLongClickOnUserTask
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.for_screen.UserTaskInfoRcView
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.ReportUserActionsInUserTaskInRcView
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.screen.alert.ExceedingTheLimitDialog
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.viewmodel.MainContentViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SAVE_USER_TASK_BUNDLE = "save_user_task_bundle"
const val MAX_COUNT_OF_TASK = 150

class MainContent : Fragment(), UserLongClickOnUserTask, ReportUserActionsInUserTaskInRcView {

    private lateinit var binding: FragmentMainContentBinding

    private val mainContentViewModel by viewModel<MainContentViewModel>()
    private val sqlManager: SqlManager by inject()

    private val navHostController: NavController by lazy {
        NavHostFragment.findNavController(this)
    }

    private val userTaskRcView: UserTaskInfoRcView by lazy {
        UserTaskInfoRcView(
            mainContentViewModel, mainContentViewModel,
            userLongClickOnUserTask = this@MainContent,
            reportUserActionsInUserTaskInRcView = this@MainContent
        )
    }

    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(
            ItemTouchHelperSwipe(
                userDeleteTask = userTaskRcView
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainContentBinding.inflate(inflater, container, false)

        showProgressBar(binding.mainProgressBar)

        val userTasks =
            savedInstanceState?.getString(SAVE_USER_TASK_BUNDLE)

        if (userTasks != null) {
            binding.edtvUserNewTask.setText(userTasks)
        }

        this.mainContentViewModel.getUserTasks()

        return binding.root
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

        mainContentViewModelUserTaskLiveDataObserverWhenObserveSubmitDataList()
        edTvUserNewTaskActionListener()
        parentFragmentManagerFragmentResultListener()
        mainProgressBarListener()

    }

    private fun mainProgressBarListener() {

        binding.mainProgressBar.setOnClickListener(View.OnClickListener {
            if (this.userTaskRcView.getAllUserTasksInfoSize() <= 0) {
                this.mainContentViewModel.getUserTasks()
            }
        })

    }

    private fun mainContentViewModelUserTaskLiveDataObserverWhenObserveSubmitDataList() {

        mainContentViewModel.userTasksLiveData.observe(viewLifecycleOwner,
            Observer {

                this.userTaskRcView.submitList(it)
                hideProgressBar(binding.mainProgressBar)

            })

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

        binding.edtvUserNewTask.setOnEditorActionListener(object : TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (userTaskRcView.getAllUserTasksInfoSize() < MAX_COUNT_OF_TASK) {

                        showProgressBar(binding.mainProgressBar)

                        val userTask = binding.edtvUserNewTask.text.toString()
                        val newUserTaskInfo = UserTaskInfo(
                            userTask, false,
                            null
                        )

                        insertUserTaskToSql(newUserTaskInfo)
                        getUserTasks()

                        hideInputMethod()
                        clearEditTextView(binding.edtvUserNewTask)

                    } else {

                        val message = resources.getString(R.string.exceeding_the_limit_of_tasks)
                        val positiveButtonText = resources.getString(R.string.ok)

                        showAlertDialogAsOneMessageAndOnePositiveButton(
                            messageForUser = message,
                            positiveButtonTextFromUser = positiveButtonText
                        )

                    }

                    handled = true

                }

                return handled
            }

        })

    }

    private fun showAlertDialogAsOneMessageAndOnePositiveButton(
        messageForUser: String, positiveButtonTextFromUser: String
    ) {

        val message = messageForUser
        val positiveButtonText = positiveButtonTextFromUser

        val alertDialog = ExceedingTheLimitDialog.getAlert(
            context = requireContext(),
            message = message,
            positiveButtonText = positiveButtonText
        )

        alertDialog.show()
    }

    private fun insertUserTaskToSql(newUserTaskInfo: UserTaskInfo) {
        this.mainContentViewModel.insertUserTaskToSql(newUserTaskInfo)
    }

    private fun getUserTasks() {
        this.mainContentViewModel.getUserTasks()
    }

    private fun clearEditTextView(edtv: EditText) {

        edtv.setText("")
        edtv.clearFocus()

    }

    private fun hideInputMethod() {

        val imm =
            activity?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)

    }

    private fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.INVISIBLE
    }

    override fun userLongClickOnUserTask(userTask: UserTaskInfo) {

        navHostController.navigate(

            R.id.action_main_content_to_user_changes_task,
            bundleOf(UserChangesTask.USER_TASK_KEY to userTask)

        )

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val userTask = binding.edtvUserNewTask.text.toString()
        outState.putString(SAVE_USER_TASK_BUNDLE, userTask)

    }

    override fun goingToDeleteElement() {
        showProgressBar(binding.mainProgressBar)
    }

    override fun removedElement() {
        hideProgressBar(binding.mainProgressBar)
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainContent()

    }

}