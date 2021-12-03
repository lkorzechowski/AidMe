package com.orzechowski.saveme.browser.categories.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey val categoryId: Long,
                val categoryName: String,
                val hasSubcategories: Boolean,
                val miniatureName: String,
                val categoryLevel: Int) //level/poziom 0 = najwyższy
