package com.orzechowski.aidme.main

import androidx.room.Entity

@Entity
data class EmergencyNumber(
    val phoneNumber: Int,
    val serviceName: String)
