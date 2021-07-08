package com.orzechowski.aidme.tutorial.version.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class VersionInstruction(
    @PrimaryKey val versionInstructionId: Long,
                val versionId: Long,
                val instructionPosition: Int
)
