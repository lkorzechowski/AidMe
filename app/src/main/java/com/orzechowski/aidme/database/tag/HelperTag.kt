package com.orzechowski.aidme.database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HelperTag(
    @PrimaryKey val helperTagId: Long,
                val helperId: Long,
                val tagId: Long)