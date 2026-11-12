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
 *
 * Хранит состояние экрана и переживает пересоздание Activity (поворот экрана):
 * результаты поиска не пропадут. Экран не знает, откуда берутся данные, —
 * он только подписывается на [searchScreenState] и вызывает [searchTracks].
 *
 * @param trackRepository репозиторий треков; передаётся через Koin.
 */
class SearchViewModel(
    private val trackRepository: TracksRepository
) : ViewModel() {

    // Изменяемое состояние доступно только внутри ViewModel.
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)

    // Наружу отдаём неизменяемый StateFlow: UI не может случайно его переписать.
    val searchScreenState: StateFlow<SearchState> = _searchScreenState.asStateFlow()

    /** Текущий запрос: нужен, чтобы отменять устаревший поиск. */
    private var searchJob: Job? = null

    /**
     * Запускает поиск по строке [query].
     *
     * Корутина запускается во [viewModelScope]: если ViewModel уничтожат,
     * запрос отменится автоматически.
     */
    fun searchTracks(query: String) {
        // Новый запрос отменяет предыдущий: иначе старый ответ мог бы перезаписать новый.
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchScreenState.value = SearchState.Searching
            try {
                val result = trackRepository.searchTracksContains(query)
                // Пустой результат — это успешный поиск, просто без совпадений.
                // Экран сам покажет «Ничего не найдено» по пустому списку.
                _searchScreenState.value = SearchState.Success(result)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _searchScreenState.value = SearchState.Fail(SEARCH_ERROR_PREFIX + e.message)
            }
        }
    }

    /** Возвращает экран в исходное состояние (например, при очистке запроса). */
    fun clearSearch() {
        searchJob?.cancel()
        _searchScreenState.value = SearchState.Initial
    }

    private companion object {
        const val SEARCH_ERROR_PREFIX = "Ошибка при поиске: "
    }
}
