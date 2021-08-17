package com.orzechowski.aidme.browser.search.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagKeyword(
    @PrimaryKey val tagKeywordId: Long,
                val keywordId: Long,
                val tagId: Long)
