package com.orzechowski.prototyp.database.tutorial

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Tutorial(@PrimaryKey val tutorialId: Long, val tutorialName: String, val authorId: Long)