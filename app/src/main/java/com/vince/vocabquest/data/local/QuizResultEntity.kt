package com.vince.vocabquest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val completedAt: Long = System.currentTimeMillis(),
)
