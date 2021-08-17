package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VersionMultimedia(
    @PrimaryKey val versionMultimediaId: Long,
                val multimediaId: Long,
                val versionId: Long)
