package com.vince.vocabquest.di

import android.content.Context
import com.vince.vocabquest.data.DictionaryRepository
import com.vince.vocabquest.data.NetworkDictionaryRepository
import com.vince.vocabquest.data.OfflineProgressRepository
import com.vince.vocabquest.data.ProgressRepository
import com.vince.vocabquest.data.SampleVocabularyRepository
import com.vince.vocabquest.data.VocabularyRepository
import com.vince.vocabquest.data.local.VocabQuestDatabase
import com.vince.vocabquest.data.remote.DictionaryApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val vocabularyRepository: VocabularyRepository
    val progressRepository: ProgressRepository
    val dictionaryRepository: DictionaryRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val database by lazy {
        VocabQuestDatabase.getDatabase(context)
    }

    private val dictionaryApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }

    override val vocabularyRepository: VocabularyRepository by lazy {
        SampleVocabularyRepository()
    }

    override val progressRepository: ProgressRepository by lazy {
        OfflineProgressRepository(database.quizResultDao())
    }

    override val dictionaryRepository: DictionaryRepository by lazy {
        NetworkDictionaryRepository(dictionaryApiService)
    }
}
