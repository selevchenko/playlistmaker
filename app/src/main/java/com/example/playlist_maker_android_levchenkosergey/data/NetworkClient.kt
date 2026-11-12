package com.example.playlist_maker_android_levchenkosergey.data

import com.example.playlist_maker_android_levchenkosergey.data.dto.BaseResponse

/**
 * Сетевой клиент слоя Data.
 */
interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}
