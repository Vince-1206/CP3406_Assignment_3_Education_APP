package com.vince.vocabquest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [QuizResultEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class VocabQuestDatabase : RoomDatabase() {
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile
        private var instance: VocabQuestDatabase? = null

        fun getDatabase(context: Context): VocabQuestDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    VocabQuestDatabase::class.java,
                    "vocabquest_database",
                ).build().also { instance = it }
            }
    }
}
