package com.example.playlist_maker_android_levchenkosergey.data.network

import com.example.playlist_maker_android_levchenkosergey.data.NetworkClient
import com.example.playlist_maker_android_levchenkosergey.data.dto.BaseResponse
import com.example.playlist_maker_android_levchenkosergey.data.dto.TracksSearchResponse

/**
 * Временная реализация сетевого клиента.
 *
 * Настоящих запросов в сеть пока нет: тему Retrofit разберём в следующих спринтах.
 * Класс уже стоит на своём месте в архитектуре, поэтому, когда появится реальная
 * реализация, остальной код (репозиторий, Domain, UI) менять не придётся.
 */
class RetrofitNetworkClient : NetworkClient {

    override fun doRequest(dto: Any): BaseResponse {
        return TracksSearchResponse(results = emptyList())
    }
}
