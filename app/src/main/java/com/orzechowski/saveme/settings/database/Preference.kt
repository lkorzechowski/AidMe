package com.orzechowski.saveme.settings.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Preference(@PrimaryKey val id: Long, val motive: Boolean)
