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
import com.release_next_project_safa_ptintln_out_systems_release_project.core.logD
import com.release_next_project_safa_ptintln_out_systems_release_project.core.logE
import com.release_next_project_safa_ptintln_out_systems_release_project.databinding.FragmentUserChangesTaskBinding
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo

class UserChangesTask(): Fragment() {

    private var _binding: FragmentUserChangesTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var args: UserTaskInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserChangesTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireArguments().getParcelable<UserTaskInfo>(USER_TASK_KEY) != null) {

            try {

                args = requireArguments().getParcelable<UserTaskInfo>(USER_TASK_KEY)
                        as UserTaskInfo
                initView(args)

            } catch (e: Exception) {
                logE("UserChangesTask args: $e")
            }

        }

        binding.edtvUserTaskChange.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.edtvUserTaskChange.setRawInputType(InputType.TYPE_CLASS_TEXT)

        binding.edtvUserTaskChange.setOnEditorActionListener { v, actionId, event ->
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

            handled
        }

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