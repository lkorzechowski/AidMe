package com.orzechowski.saveme.database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HelperTag(
    @PrimaryKey val helperTagId: Long,
                val helperId: Long,
                val tagId: Long)
