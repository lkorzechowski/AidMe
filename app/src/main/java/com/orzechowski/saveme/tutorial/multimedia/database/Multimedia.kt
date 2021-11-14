package com.orzechowski.saveme.tutorial.multimedia.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Multimedia(
    @PrimaryKey var multimediaId: Long,
                var tutorialId: Long,
                var displayTime: Int,
                var type: Boolean,  //true = obrazek, false = filmik
                var fileName: String,
                var loop: Boolean,
                var position: Int)
