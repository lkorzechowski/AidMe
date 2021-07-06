package com.orzechowski.aidme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.login.LoginFragment

class LoginActivity : AppCompatActivity() {
    val mLogin = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.login_fragment_bed, mLogin)
        }
    }
}