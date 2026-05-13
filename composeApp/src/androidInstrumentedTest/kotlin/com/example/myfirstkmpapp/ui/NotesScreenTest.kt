package com.example.myfirstkmpapp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.myfirstkmpapp.data.Note
import com.example.myfirstkmpapp.data.NoteRepository
import com.example.myfirstkmpapp.screens.NotesScreen
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class NotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository: NoteRepository = mockk()
    private val notesFlow = MutableStateFlow<List<Note>>(emptyList())

    @Test
    fun notesScreen_displaysEmptyStateWhenNoNotes() {
        coEvery { repository.getAllNotes() } returns notesFlow
        val viewModel = NotesViewModel(repository)

        composeTestRule.setContent {
            NotesScreen(
                viewModel = viewModel,
                onNoteClick = {},
                onAddNoteClick = {}
            )
        }

        composeTestRule.onNodeWithText("Belum ada catatan.").assertIsDisplayed()
    }

    @Test
    fun notesScreen_displaysNoteListCorrectly() {
        val fakeNotes = listOf(
            Note(1, "Title 1", "Content 1"),
            Note(2, "Title 2", "Content 2")
        )
        notesFlow.value = fakeNotes
        coEvery { repository.getAllNotes() } returns notesFlow
        val viewModel = NotesViewModel(repository)

        composeTestRule.setContent {
            NotesScreen(
                viewModel = viewModel,
                onNoteClick = {},
                onAddNoteClick = {}
            )
        }

        composeTestRule.onNodeWithText("Title 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title 2").assertIsDisplayed()
    }

    @Test
    fun addNoteButton_isClickableAndTriggersAction() {
        coEvery { repository.getAllNotes() } returns notesFlow
        val viewModel = NotesViewModel(repository)
        var addClicked = false

        composeTestRule.setContent {
            NotesScreen(
                viewModel = viewModel,
                onNoteClick = {},
                onAddNoteClick = { addClicked = true }
            )
        }

        composeTestRule.onNodeWithContentDescription("Add Note").performClick()
        assert(addClicked)
    }
}
