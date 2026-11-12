package com.example.playlist_maker_android_levchenkosergey.ui.view_model

import com.example.playlist_maker_android_levchenkosergey.domain.model.Track

/**
 * Состояния экрана поиска.
 *
 * `sealed class` — потому что список состояний закрытый: компилятор проверит,
 * что `when` на экране обрабатывает их все. Если добавим новое состояние,
 * компилятор укажет на все места, где его не хватает.
 */
sealed class SearchState {

    /** Экран открыт, пользователь ещё ничего не искал. */
    object Initial : SearchState()

    /** Идёт поиск: показываем прогресс-бар. */
    object Searching : SearchState()

    /** Поиск завершён успешно: показываем список найденных треков. */
    data class Success(val foundList: List<Track>) : SearchState()

    /** Поиск завершился ошибкой: показываем сообщение. */
    data class Fail(val error: String) : SearchState()
}
