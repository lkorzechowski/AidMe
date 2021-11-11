package com.orzechowski.saveme.tutorial.mediaplayer.multimedia.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Multimedia(
    @PrimaryKey val multimediaId: Long,
                var tutorialId: Long,
                var displayTime: Int,
                val type: Boolean,  //true = image, false = video
                var fileUriString: String,
                var loop: Boolean,
                var position: Int)
