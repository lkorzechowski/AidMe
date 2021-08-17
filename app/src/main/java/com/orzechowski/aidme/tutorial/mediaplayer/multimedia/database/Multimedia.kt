package com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Multimedia(
    @PrimaryKey val multimediaId: Long,
                val tutorialId: Long,
                var displayTime: Int,
                val type: Boolean,  //true = image, false = video
                val fullFileName: String,
                var loop: Boolean,
                var position: Int)
