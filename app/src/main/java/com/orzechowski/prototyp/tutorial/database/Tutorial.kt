package com.orzechowski.prototyp.tutorial.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Tutorial(
    @PrimaryKey val tutorialId: Long,
                val tutorialName: String,
                val authorId: Long,
                val globalSoundStart: Long,
                val globalSoundLoop: Boolean,
                val globalSoundLoopInterval: Long)