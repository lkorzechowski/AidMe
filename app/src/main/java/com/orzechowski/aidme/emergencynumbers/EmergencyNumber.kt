package com.orzechowski.aidme.emergencynumbers

import androidx.room.Entity

@Entity
data class EmergencyNumber(
    val phoneNumber: Int,
    val serviceName: String)
