package com.example.playlist_maker_android_levchenkosergey.data.network

import com.example.playlist_maker_android_levchenkosergey.data.NetworkClient
import com.example.playlist_maker_android_levchenkosergey.data.dto.BaseResponse
import com.example.playlist_maker_android_levchenkosergey.data.dto.TracksSearchResponse

/**
 * Заглушка сетевого клиента.
 */
class RetrofitNetworkClient : NetworkClient {

    override fun doRequest(dto: Any): BaseResponse {
        return TracksSearchResponse(results = emptyList())
    }
}
