package com.orzechowski.saveme.tutorial.version.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Version(
    @PrimaryKey var versionId: Long,
                var text: String,
                var tutorialId: Long,
                var delayGlobalSound: Boolean,
                var hasChildren: Boolean,
                var hasParent: Boolean,
                var parentVersionId: Long?)
