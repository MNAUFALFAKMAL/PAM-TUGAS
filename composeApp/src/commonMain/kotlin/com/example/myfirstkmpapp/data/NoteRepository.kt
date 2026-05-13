package com.example.myfirstkmpapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id: Int): Note?
    fun insertNote(title: String, content: String)
    fun updateNote(id: Int, title: String, content: String)
    fun deleteNote(id: Int)
    fun toggleFavorite(id: Int)
}

class InMemoryNoteRepository : NoteRepository {
    private val _notes = MutableStateFlow(
        listOf(
            Note(1, "Impian", "Kaya raya dan bahagia", true),
            Note(2, "Daftar Belanja", "Eskrim, cemilan, dan minuman.", false)
        )
    )

    override fun getAllNotes(): Flow<List<Note>> = _notes.asStateFlow()

    override fun getNoteById(id: Int): Note? {
        return _notes.value.find { it.id == id }
    }

    override fun insertNote(title: String, content: String) {
        _notes.update { currentList ->
            val newId = if (currentList.isEmpty()) 1 else currentList.maxOf { it.id } + 1
            currentList + Note(id = newId, title = title, content = content)
        }
    }

    override fun updateNote(id: Int, title: String, content: String) {
        _notes.update { currentList ->
            currentList.map { if (it.id == id) it.copy(title = title, content = content) else it }
        }
    }

    override fun deleteNote(id: Int) {
        _notes.update { currentList -> currentList.filter { it.id != id } }
    }

    override fun toggleFavorite(id: Int) {
        _notes.update { currentList ->
            currentList.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
        }
    }
}
