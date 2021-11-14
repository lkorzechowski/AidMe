package com.orzechowski.saveme.tutorial.multimedia.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VersionMultimedia(
    @PrimaryKey val versionMultimediaId: Long,
                var multimediaId: Long,
                var versionId: Long)
