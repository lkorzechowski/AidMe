package com.orzechowski.saveme.main.emergencynumber

import androidx.room.Entity

@Entity
data class EmergencyNumber(
    val phoneNumber: Int,
    val serviceName: String)
