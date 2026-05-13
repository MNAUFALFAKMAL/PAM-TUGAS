package com.example.myfirstkmpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmpapp.data.Note
import com.example.myfirstkmpapp.data.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {
    
    val notes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addNote(title: String, content: String) {
        repository.insertNote(title, content)
    }

    fun updateNote(id: Int, newTitle: String, newContent: String) {
        repository.updateNote(id, newTitle, newContent)
    }

    fun deleteNote(id: Int) {
        repository.deleteNote(id)
    }

    fun toggleFavorite(id: Int) {
        repository.toggleFavorite(id)
    }

    fun getNoteById(id: Int): Note? {
        return repository.getNoteById(id)
    }
}
