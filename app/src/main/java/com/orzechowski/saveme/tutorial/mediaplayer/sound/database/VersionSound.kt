package com.orzechowski.saveme.tutorial.mediaplayer.sound.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VersionSound(
    @PrimaryKey val versionSoundId: Long,
                var tutorialSoundId: Long,
                var versionId: Long)
