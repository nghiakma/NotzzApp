package com.example.notzzapp.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import com.example.notzzapp.datastore.UIModeDataStore
import com.example.notzzapp.model.Notes
import thecodemonks.org.nottzapp.repo.NotesRepo
import thecodemonks.org.nottzapp.utils.NotesViewState
import javax.inject.Inject

class NotesViewModel @Inject internal constructor(
    application: Application,
    private val notesRepo: NotesRepo,
) :
    AndroidViewModel(application) {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<NotesViewState>(NotesViewState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState = _uiState.asStateFlow()

    // DataStore
    private val uiDataStore = UIModeDataStore(application)

    // get UI mode
    val getUIMode = uiDataStore.uiMode

    // save UI mode
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(IO) {
            uiDataStore.saveToDataStore(isNightMode)
        }
    }

    // save notes
    fun insertNotes(taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = Notes(
            title = taskName,
            description = taskDesc
        )
        notesRepo.insert(notes)
    }

    // update notes
    fun updateNotes(id: Int, taskName: String, taskDesc: String) = viewModelScope.launch {
        val notes = Notes(
            id = id,
            title = taskName,
            description = taskDesc
        )
        notesRepo.update(notes)
    }

    // get all saved notes by default
    init {
        viewModelScope.launch {
            notesRepo.getSavedNotes().distinctUntilChanged().collect { result ->
                if (result.isNullOrEmpty()) {
                    _uiState.value = NotesViewState.Empty
                } else {
                    _uiState.value = NotesViewState.Success(result)
                }
            }
        }
    }

    // delete note by ID
    fun deleteNoteByID(id: Int) = viewModelScope.launch {
        notesRepo.deleteNote(id)
    }
}
