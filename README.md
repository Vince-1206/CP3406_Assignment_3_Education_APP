# VocabQuest

VocabQuest is a vocabulary-learning Android app for secondary-school ESL students aged 12–18. It combines short study cards with multiple-choice quizzes and clear progress feedback.

This repository contains Han-Wei Lin's CP3406 Assessment 3 project.

## Current milestone

Milestone 5 adds a live dictionary service to the connected learning flow:

- Kotlin and Jetpack Compose
- Material Design 3 interface
- Four required assignment screens: Landing, Activity, Settings, and User Statistics
- Separate Learn Vocabulary screen so definitions are studied before the quiz
- Ten secondary-school ESL vocabulary items in the starter lesson
- Quiz questions that keep definitions hidden until an answer is submitted
- Quiz results with explicit Home, Progress, and Try Again actions
- Connected Home → Learn 10 words → Quiz 10 questions → Results lesson flow
- Navigation Component with Home, Progress, and Settings in the persistent bottom bar
- Quiz is reached through the lesson instead of bypassing learning from the bottom bar
- Room database stores completed quiz results locally on the device
- Home overview and Progress statistics update from saved quiz results
- Progress shows real average, latest, and best scores instead of sample values
- Retrofit connects the learning screen to the Free Dictionary API
- Live pronunciation, part of speech, definition, and example data when available
- Loading, retry, and offline fallback states for every dictionary request
- In-memory caching avoids repeating requests for words already viewed
- ViewModel and Repository separation
- Simple dependency container
- Sample vocabulary repository and quiz logic
- Non-GUI model tests and a starter Compose UI test
- Accessibility-minded controls and privacy-by-design explanations

Quiz statistics are calculated from locally saved Room data. Dictionary details come from a live API when the device has internet access, while the curated lesson remains available offline.

## Planned features

1. Store settings and teacher-created questions locally.
2. Add a simple local quiz creator without requiring student accounts.
3. Expand model and Compose UI testing.
4. Add responsive layouts and further UI polish.

## App structure

```text
app/src/main/java/com/vince/vocabquest/
├── data/          Repository interface and data implementations
├── di/            Application dependency container
├── model/         Vocabulary and quiz models
└── ui/
    ├── navigation/
    ├── screens/
    └── theme/
```

## Run the project

1. Clone or download this repository.
2. Open the project folder in Android Studio.
3. Allow Gradle Sync to finish.
4. Select an Android emulator or connected device using API 26 or later.
5. Click **Run app**.

The project uses Java 17, Android Gradle Plugin 8.13.2, compile SDK 36, and the stable Compose BOM.
An internet connection enables the Live Dictionary card, but the learning and quiz flow also works offline.

## Ethical design direction

- No account, name, email, or location is required.
- Progress is intended to remain on the learner's device.
- Dictionary requests contain the selected word and do not add a learner name, email, or location.
- The interface identifies online information as coming from the Free Dictionary API.
- The interface uses readable typography, labelled navigation, large controls, and reduced-motion settings.
- Content is designed to be appropriate for secondary-school learners.
- Feedback supports learning without public rankings or manipulative streak pressure.

## Assignment status

- [x] App concept and target audience
- [x] Compose project foundation
- [x] Four core screens
- [x] Navigation foundation
- [x] Connected Learn → Quiz lesson experience
- [x] Ten-word starter vocabulary lesson
- [x] Starter ViewModel and Repository architecture
- [x] Room database for persistent quiz results
- [x] Real Home and Progress statistics from saved results
- [x] External dictionary API integration with Retrofit
- [x] Loading, retry, caching, and offline fallback behaviour
- [x] Starter unit and GUI tests
- [x] Persistent settings
- [x] Teacher quiz creator
- [x] Final testing and UI polish
- [x]1000-word Gibbs' Reflective Cycle self-reflection
- [x] Declaration of AI-Generated Material
