package com.orzechowski.aidme.tutorial.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TutorialLink(
    @PrimaryKey val tutorialLinkId: Long,
                val tutorialId: Long,
                val originId: Long,
                val instructionNumber: Int)
