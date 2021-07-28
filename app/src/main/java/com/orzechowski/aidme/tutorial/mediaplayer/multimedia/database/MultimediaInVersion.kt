package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MultimediaInVersion(
    @PrimaryKey val multimediaInVersionId: Long,
                val multimediaId: Long,
                val versionId: Long)
