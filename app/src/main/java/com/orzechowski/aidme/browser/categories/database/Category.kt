package com.orzechowski.aidme.browser.categories.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey val categoryId: Long,
                val categoryName: String,
                val hasSubcategories: Boolean,
                val miniatureUriString: String,
                val categoryLevel: Int) //level 0 = highest
