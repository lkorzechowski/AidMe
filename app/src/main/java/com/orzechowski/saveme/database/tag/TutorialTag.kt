package com.orzechowski.saveme.database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TutorialTag(
    @PrimaryKey val tutorialTagId: Long,
                var tutorialId: Long,
                var tagId: Long)
