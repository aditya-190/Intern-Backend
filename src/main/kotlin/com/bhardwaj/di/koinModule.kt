package com.bhardwaj.di

import com.bhardwaj.repository.*
import org.koin.dsl.module

val categoryModule = module {
    single<CategoryRepository> {
        CategoryRepositoryImpl()
    }
}

val examModule = module {
    single<ExamRepository> {
        ExamRepositoryImpl()
    }
}

val filterModule = module {
    single<FilterRepository> {
        FilterRepositoryImpl()
    }
}

val jobModule = module {
    single<JobRepository> {
        JobRepositoryImpl()
    }
}

val languageModule = module {
    single<LanguageRepository> {
        LanguageRepositoryImpl()
    }
}

val userModule = module {
    single<UserRepository> {
        UserRepositoryImpl()
    }
}