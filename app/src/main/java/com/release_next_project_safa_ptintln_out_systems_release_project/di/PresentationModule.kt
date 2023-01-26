package com.release_next_project_safa_ptintln_out_systems_release_project.di

import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.UserClickOnTaskCheckBox
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.UserInteractsWithTasks
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.UserLongClickOnUserTask
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.viewmodel.MainContentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.binds
import org.koin.dsl.module

val presentationModule = module {

    viewModel<MainContentViewModel> {

        MainContentViewModel(communicateWithLocalSqlDatabaseUseCase = get())

    }.binds(
        arrayOf(

        UserInteractsWithTasks::class,
        UserClickOnTaskCheckBox::class,
        UserLongClickOnUserTask::class

        )
    )

}