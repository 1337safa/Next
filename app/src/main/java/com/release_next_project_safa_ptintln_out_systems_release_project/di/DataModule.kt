package com.release_next_project_safa_ptintln_out_systems_release_project.di

import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.repository.UserTaskLocalSqlRepositoryImpl
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlManager
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.repository.UserTaskLocalSqlRepository
import org.koin.dsl.module

val dataModule = module {

    single<UserTaskLocalSqlRepository> {
        UserTaskLocalSqlRepositoryImpl(sqlManager = get())
    }

    single<SqlManager> {

        SqlManager(context = get())

    }

}