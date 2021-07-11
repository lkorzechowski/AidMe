package com.orzechowski.aidme.settings.helper.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Helper(
    @PrimaryKey val helperId: Long,
                val name: String,
                val surname: String,
                val title: String,
                val profession: String,
                val tags: String)
