package com.orzechowski.saveme.tutorial.mediaplayer.sound.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TutorialSound(
    @PrimaryKey val soundId: Long,
                var soundStart: Long,
                var soundLoop: Boolean,
                var interval: Long,
                var tutorialId: Long,
                var fileName: String)
