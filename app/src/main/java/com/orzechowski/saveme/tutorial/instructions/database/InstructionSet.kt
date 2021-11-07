package com.orzechowski.saveme.tutorial.instructions.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InstructionSet(
    @PrimaryKey val instructionSetId: Long,
                var title: String,
                var instructions: String,
                var time: Int,
                var tutorialId: Long,
                var position: Int,
                var narrationFile: String?)
