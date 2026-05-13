package com.example.myfirstkmpapp.viewmodel

import app.cash.turbine.test
import com.example.myfirstkmpapp.data.Note
import com.example.myfirstkmpapp.data.NoteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private lateinit var viewModel: NotesViewModel
    private val repository: NoteRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val fakeNotes = listOf(
        Note(1, "Title 1", "Content 1"),
        Note(2, "Title 2", "Content 2")
    )
    private val notesFlow = MutableStateFlow(fakeNotes)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repository.getAllNotes() } returns notesFlow
        viewModel = NotesViewModel(repository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadNotes_updatesUiStateSuccessfully() = runTest {
        viewModel.notes.test {
            // State awal dari stateIn (initialValue)
            assertEquals(emptyList(), awaitItem())
            // Data dari repository
            assertEquals(fakeNotes, awaitItem())
        }
    }

    @Test
    fun addNote_callsRepositoryInsert() = runTest {
        val title = "New Note"
        val content = "New Content"
        coEvery { repository.insertNote(any(), any()) } returns Unit

        viewModel.addNote(title, content)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.insertNote(title, content) }
    }

    @Test
    fun deleteNote_callsRepositoryDelete() = runTest {
        val id = 1
        coEvery { repository.deleteNote(any()) } returns Unit

        viewModel.deleteNote(id)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.deleteNote(id) }
    }

    @Test
    fun updateNote_callsRepositoryUpdate() = runTest {
        val id = 1
        val title = "Updated"
        val content = "Updated"
        coEvery { repository.updateNote(any(), any(), any()) } returns Unit

        viewModel.updateNote(id, title, content)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.updateNote(id, title, content) }
    }

    @Test
    fun toggleFavorite_callsRepositoryToggle() = runTest {
        val id = 1
        coEvery { repository.toggleFavorite(any()) } returns Unit

        viewModel.toggleFavorite(id)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.toggleFavorite(id) }
    }

    @Test
    fun getNoteById_returnsCorrectNoteFromRepo() = runTest {
        val id = 1
        val expectedNote = fakeNotes[0]
        coEvery { repository.getNoteById(id) } returns expectedNote

        val result = viewModel.getNoteById(id)

        assertEquals(expectedNote, result)
        coVerify { repository.getNoteById(id) }
    }

    // --- TURBINE FLOW TESTS ---

    @Test
    fun notesStateFlow_emitsInitialEmptyThenData() = runTest {
        viewModel.notes.test {
            // Case 1: Memastikan emisi pertama adalah list kosong (loading state)
            assertEquals(emptyList(), awaitItem())
            // Case 2: Memastikan emisi kedua adalah data dari repo
            assertEquals(fakeNotes, awaitItem())
        }
    }

    @Test
    fun notesStateFlow_emitsUpdatedListAfterInsertion() = runTest {
        viewModel.notes.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(fakeNotes, awaitItem())

            // Simulate repository update
            val updatedNotes = fakeNotes + Note(3, "Title 3", "Content 3")
            notesFlow.value = updatedNotes

            assertEquals(updatedNotes, awaitItem())
        }
    }

    @Test
    fun notesStateFlow_reflectsMultipleChanges() = runTest {
        viewModel.notes.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(fakeNotes, awaitItem())

            // Change 1: Hapus satu
            val list1 = fakeNotes.filter { it.id != 1 }
            notesFlow.value = list1
            assertEquals(list1, awaitItem())

            // Change 2: Tambah satu
            val list2 = list1 + Note(4, "Title 4", "Content 4")
            notesFlow.value = list2
            assertEquals(list2, awaitItem())
        }
    }

    @Test
    fun getNoteById_returnsNullWhenRepoReturnsNull() = runTest {
        val id = 99
        coEvery { repository.getNoteById(id) } returns null
        val result = viewModel.getNoteById(id)
        assertEquals(null, result)
    }
}
