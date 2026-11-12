package com.example.playlist_maker_android_levchenkosergey.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_levchenkosergey.domain.api.TracksRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel экрана поиска.
 */
class SearchViewModel(
    private val trackRepository: TracksRepository
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState: StateFlow<SearchState> = _searchScreenState.asStateFlow()

    private var searchJob: Job? = null

    fun searchTracks(query: String) {
        // Новый запрос отменяет предыдущий.
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchScreenState.value = SearchState.Searching
            try {
                val result = trackRepository.searchTracksContains(query)
                _searchScreenState.value = SearchState.Success(result)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _searchScreenState.value = SearchState.Fail(SEARCH_ERROR_PREFIX + e.message)
            }
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        _searchScreenState.value = SearchState.Initial
    }

    private companion object {
        const val SEARCH_ERROR_PREFIX = "Ошибка при поиске: "
    }
}
