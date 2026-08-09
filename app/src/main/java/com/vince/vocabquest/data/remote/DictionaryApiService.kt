package com.vince.vocabquest.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("entries/en/{word}")
    suspend fun getWord(@Path("word") word: String): List<DictionaryEntryDto>
}
