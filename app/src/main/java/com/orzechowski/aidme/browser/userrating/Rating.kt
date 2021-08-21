package com.orzechowski.aidme.browser.userrating

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rating(
    @PrimaryKey val ratingId: Long,
                val tutorialId: Long)
