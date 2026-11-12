package com.example.playlist_maker_android_levchenkosergey.domain.api

import com.example.playlist_maker_android_levchenkosergey.domain.model.Track

/**
 * «Дверь» слоя Domain к данным.
 *
 * Интерфейс описывает, ЧТО можно сделать (найти треки по подстроке), но не КАК:
 * реализация живёт в слое Data и может быть заменена, например, на реальный
 * сетевой запрос, без изменений в Domain и UI.
 */
interface TracksRepository {

    /**
     * Ищет треки, в названии или имени исполнителя которых встречается [expression].
     *
     * Функция `suspend`, потому что получение данных — потенциально долгая операция:
     * её нельзя выполнять в главном потоке.
     */
    suspend fun searchTracksContains(expression: String): List<Track>
}
