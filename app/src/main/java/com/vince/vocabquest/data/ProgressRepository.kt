package com.vince.vocabquest.data

import com.vince.vocabquest.data.local.QuizResultDao
import com.vince.vocabquest.data.local.QuizResultEntity
import com.vince.vocabquest.model.QuizResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ProgressSummary(
    val averageScore: Int = 0,
    val quizzesFinished: Int = 0,
    val latestScore: Int = 0,
    val bestScore: Int = 0,
    val questionsCompleted: Int = 0,
)

fun calculateProgressSummary(results: List<QuizResult>): ProgressSummary {
    if (results.isEmpty()) return ProgressSummary()

    return ProgressSummary(
        averageScore = results.map { it.percentage }.average().toInt(),
        quizzesFinished = results.size,
        latestScore = results.first().percentage,
        bestScore = results.maxOf { it.percentage },
        questionsCompleted = results.sumOf { it.totalQuestions },
    )
}

interface ProgressRepository {
    fun observeProgress(): Flow<ProgressSummary>
    suspend fun saveQuizResult(result: QuizResult)
}

class OfflineProgressRepository(
    private val quizResultDao: QuizResultDao,
) : ProgressRepository {
    override fun observeProgress(): Flow<ProgressSummary> =
        quizResultDao.observeAll().map { entities ->
            calculateProgressSummary(
                entities.map { entity ->
                    QuizResult(
                        correctAnswers = entity.correctAnswers,
                        totalQuestions = entity.totalQuestions,
                    )
                },
            )
        }

    override suspend fun saveQuizResult(result: QuizResult) {
        quizResultDao.insert(
            QuizResultEntity(
                correctAnswers = result.correctAnswers,
                totalQuestions = result.totalQuestions,
            ),
        )
    }
}
