package com.orzechowski.saveme.tutorial.version.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VersionInstruction(
    @PrimaryKey val versionInstructionId: Long,
                var versionId: Long,
                val instructionPosition: Int)
