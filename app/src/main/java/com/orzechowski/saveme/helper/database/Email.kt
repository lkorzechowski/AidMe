package com.orzechowski.saveme.helper.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Email(@PrimaryKey val email: String)
