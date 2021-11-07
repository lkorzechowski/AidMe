package com.orzechowski.saveme.tutorial.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TutorialLink(
    @PrimaryKey val tutorialLinkId: Long,
                val tutorialId: Long,
                var originId: Long,
                val instructionNumber: Int)
