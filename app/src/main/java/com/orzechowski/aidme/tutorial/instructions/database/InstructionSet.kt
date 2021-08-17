package com.orzechowski.aidme.tutorial.instructions.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InstructionSet(
    @PrimaryKey val instructionSetId: Long,
                var title: String,
                var instructions: String,
                var time: Int,
                val tutorialId: Long,
                var position: Int,
                val narrationFileName: String?)
