package com.orzechowski.aidme.tutorial.version.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Version(
    @PrimaryKey val versionId: Long,
                var text: String,
                val tutorialId: Long,
                var delayGlobalSound: Boolean,
                var hasChildren: Boolean,
                var hasParent: Boolean,
                var parentVersionId: Long?)
