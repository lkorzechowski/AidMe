package com.orzechowski.aidme.database.helper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Helper(
    @PrimaryKey val helperId: Long,
                val name: String,
                val surname: String,
                val title: String,
                val profession: String)
