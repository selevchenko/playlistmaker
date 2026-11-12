package com.example.playlist_maker_android_levchenkosergey.data

import com.example.playlist_maker_android_levchenkosergey.data.dto.BaseResponse

/**
 * Абстракция сетевого клиента для слоя Data.
 *
 * Слою Domain не важно, чем именно ходить в сеть (Retrofit, OkHttp, Ktor),
 * поэтому репозиторий зависит от этого интерфейса, а не от конкретной библиотеки.
 * Реальная реализация появится в следующих спринтах, когда будем разбирать сеть.
 */
interface NetworkClient {

    /** Выполняет запрос [dto] и возвращает ответ сервера. */
    fun doRequest(dto: Any): BaseResponse
}
