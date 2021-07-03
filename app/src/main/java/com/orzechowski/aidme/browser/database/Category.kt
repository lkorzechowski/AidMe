package com.orzechowski.aidme.browser.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Category(
    @PrimaryKey val categoryId: Long,
                val categoryName: String,
                val categoryTags: String,
                val hasSubcategories: Boolean,
                val miniatureName: String,
                val categoryLevel: Int) //level 0 = highest
