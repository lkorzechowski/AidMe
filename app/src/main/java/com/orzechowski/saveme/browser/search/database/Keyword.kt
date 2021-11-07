package com.orzechowski.saveme.browser.search.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Keyword(
    @PrimaryKey val keywordId: Long,
                val word: String)
