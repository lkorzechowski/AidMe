package com.orzechowski.aidme.browser.search.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Keyword(
    @PrimaryKey val keywordId: Long,
                val word: String)