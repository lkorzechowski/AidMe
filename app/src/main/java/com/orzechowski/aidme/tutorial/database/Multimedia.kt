package com.orzechowski.aidme.tutorial.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Multimedia(
    @PrimaryKey val multimediaId: Long,
                val tutorialId: Long,
                val displayTime: Int,
                val type: Boolean,  //true = image, false = video
                val loop: Boolean,
                val position: Int)