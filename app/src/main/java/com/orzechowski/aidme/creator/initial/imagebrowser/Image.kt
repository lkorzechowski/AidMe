package com.orzechowski.aidme.creator.initial.imagebrowser

import android.net.Uri

data class Image(
    val id: Long,
    val name: String,
    val width: Int,
    val height: Int,
    val contentUri: Uri)
