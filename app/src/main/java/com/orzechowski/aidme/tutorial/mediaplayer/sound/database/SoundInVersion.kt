package com.orzechowski.aidme.tutorial.mediaplayer.sound.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SoundInVersion(
    @PrimaryKey val soundInVersionId: Long,
                val tutorialSoundId: Long,
                val versionId: Long)
