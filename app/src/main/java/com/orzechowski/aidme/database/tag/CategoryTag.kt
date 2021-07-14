package com.orzechowski.aidme.database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CategoryTag(
    @PrimaryKey val categoryTagId: Long,
                val categoryId: Long,
                val tagId: Long)