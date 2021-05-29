package com.orzechowski.prototyp.emergencynumbers

import androidx.room.Entity

@Entity
class EmergencyNumber(val phoneNumber: Int, val serviceName: String)