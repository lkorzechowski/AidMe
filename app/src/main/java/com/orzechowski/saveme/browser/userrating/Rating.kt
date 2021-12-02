package com.orzechowski.saveme.browser.userrating

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rating(@PrimaryKey val tutorialId: Long)
