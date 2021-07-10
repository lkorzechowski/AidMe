package com.orzechowski.aidme.tutorial.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MultimediaInVersion(
    @PrimaryKey val multimediaInVersionId: Long,
                val multimediaId: Long,
                val versionId: Long
)