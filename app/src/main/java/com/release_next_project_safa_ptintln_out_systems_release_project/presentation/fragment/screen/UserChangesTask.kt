package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.screen

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.release_next_project_safa_ptintln_out_systems_release_project.databinding.FragmentUserChangesTaskBinding
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo


class UserChangesTask(): Fragment() {

    private val binding by lazy {
        FragmentUserChangesTaskBinding.inflate(layoutInflater)
    }

    private lateinit var args: UserTaskInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireArguments().getParcelable<UserTaskInfo>(USER_TASK_KEY) != null) {

            try {

                args = requireArguments().getParcelable<UserTaskInfo>(USER_TASK_KEY) as UserTaskInfo
                initView(args)

            } catch (e: Exception) {
                Log.d("MyLog", "UserChangesTask args: $e")
            }

        }

        binding.edtvUserTaskChange.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.edtvUserTaskChange.setRawInputType(InputType.TYPE_CLASS_TEXT)

        binding.edtvUserTaskChange.setOnEditorActionListener(object: TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                val handled = false

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    val userTask = binding.edtvUserTaskChange.text.toString()

                    if (userTask.isNotEmpty()) {
                        args.userTask = userTask

                        parentFragmentManager.setFragmentResult(
                            USER_TASK_REQUEST_KEY,
                            bundleOf(USER_TASK_KEY to args)
                        )

                        NavHostFragment.findNavController(this@UserChangesTask).popBackStack()

                    }
                }

                return handled
            }

        })

        binding.btnGoBack.setOnClickListener(View.OnClickListener {
            NavHostFragment.findNavController(this@UserChangesTask).popBackStack()
        })

    }

    private fun initView(userTask: UserTaskInfo) {
        binding.edtvUserTaskChange.setText(userTask.userTask)
    }

    companion object {

        @JvmStatic
        fun newInstance() = UserChangesTask()
        // :) :P ;O

        const val USER_TASK_KEY = "user_task_key1337"
        const val USER_TASK_REQUEST_KEY = "user_task_request_key"

    }

}