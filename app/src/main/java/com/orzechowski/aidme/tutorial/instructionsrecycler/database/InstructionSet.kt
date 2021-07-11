package com.orzechowski.aidme.tutorial.instructionsrecycler.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class InstructionSet(
    @PrimaryKey val instructionSetId: Long,
                val title: String,
                val instructions: String,
                val time: Int,
                val tutorialId: Long,
                val position: Int)