package com.example.myfirstkmpapp.di

import com.example.myfirstkmpapp.data.InMemoryNoteRepository
import com.example.myfirstkmpapp.data.NoteRepository
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import com.example.myfirstkmpapp.viewmodel.ProfileViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val dataModule = module {
    single<NoteRepository> { InMemoryNoteRepository() }
}

val viewModelModule = module {
    viewModelOf(::NotesViewModel)
    viewModelOf(::ProfileViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(dataModule, viewModelModule)
    }
}
