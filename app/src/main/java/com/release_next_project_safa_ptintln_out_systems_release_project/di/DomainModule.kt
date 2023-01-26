package com.release_next_project_safa_ptintln_out_systems_release_project.di

import com.release_next_project_safa_ptintln_out_systems_release_project.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    factory<CommunicateWithLocalSqlDatabaseUseCase> {

        CommunicateWithLocalSqlDatabaseUseCase(userTaskLocalSqlRepository = get())

    }

}