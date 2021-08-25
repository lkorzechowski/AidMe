package com.orzechowski.aidme

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class HelperActivity : AppCompatActivity()
{
    private var helping = false
    private val red = ColorStateList.valueOf(Color.argb(100, 255, 0, 0))
    private val green = ColorStateList.valueOf(Color.argb(100, 0, 255, 0))

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)
        val helperToggleButton = findViewById<ExtendedFloatingActionButton>(R.id.helper_toggle_help)
        helperToggleButton.backgroundTintList = red
        helperToggleButton.setOnClickListener {
            if(!helping) {
                helping = true
                helperToggleButton.backgroundTintList = green
                helperToggleButton.setIconResource(R.drawable.ic_check)
            } else {
                helping = false
                helperToggleButton.backgroundTintList = red
                helperToggleButton.setIconResource(R.drawable.ic_cross)
            }
        }
        val createTutorialButton = findViewById<Button>(R.id.helper_create_tutorial)
        createTutorialButton.setOnClickListener {
            val creator = Intent(this@HelperActivity, CreatorActivity::class.java)
            startActivity(creator)
        }
    }
}