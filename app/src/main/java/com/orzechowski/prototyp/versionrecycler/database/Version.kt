package com.orzechowski.prototyp.versionrecycler.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Version(@PrimaryKey val versionId: Long, val text: String, val instructions: String, val tutorialId: Long)