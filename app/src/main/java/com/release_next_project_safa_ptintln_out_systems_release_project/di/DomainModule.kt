package com.release_next_project_safa_ptintln_out_systems_release_project.di

import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlManager
import org.koin.dsl.module

val domainModule = module {

    factory<SqlManager> {
        SqlManager.getSqlDatabase(context = get())
    }

}