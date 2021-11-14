package com.orzechowski.saveme.tutorial.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tutorial(
    @PrimaryKey val tutorialId: Long,
                val tutorialName: String,
                val authorId: Long,
                val miniatureName: String,
                val rating: Float)
