package com.orzechowski.saveme.tutorial.sound.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TutorialSound(
    @PrimaryKey var soundId: Long,
                var soundStart: Long,
                var soundLoop: Boolean,
                var interval: Long,
                var tutorialId: Long,
                var fileName: String)
