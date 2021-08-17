package com.orzechowski.aidme.database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(
    @PrimaryKey val tagId: Long,
                val tagName: String,
                val tagLevel: Int?)
