package com.orzechowski.aidme.tutorial.mediaplayer.sound.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TutorialSound(
    @PrimaryKey val soundId: Long,
                var soundStart: Long,
                var soundLoop: Boolean,
                var interval: Long,
                val tutorialId: Long,
                var fileName: String)
