package com.orzechowski.aidme.tutorial.version.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Version(
    @PrimaryKey val versionId: Long,
                var text: String,
                val tutorialId: Long,
                var delayGlobalSound: Boolean,
                val hasChildren: Boolean,
                val hasParent: Boolean,
                val parentVersionId: Long?)
