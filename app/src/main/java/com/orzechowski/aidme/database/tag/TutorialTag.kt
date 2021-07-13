package com.orzechowski.aidme.database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TutorialTag(
    @PrimaryKey val tutorialTagId: Long,
                val tutorialId: Long,
                val tagId: Long)