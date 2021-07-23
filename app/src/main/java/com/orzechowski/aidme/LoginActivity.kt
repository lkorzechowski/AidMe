package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.login.LoginFragment
import com.orzechowski.aidme.login.RegisterFragment

class LoginActivity : AppCompatActivity() {
    private val mLogin = LoginFragment()
    private val mRegister = RegisterFragment()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        commitLogin()
        val switchButton = findViewById<Button>(R.id.login_switch_mode_button)
        val submitButton = findViewById<Button>(R.id.login_submit_button)
        switchButton.setOnClickListener {
            val fragmentList: List<*> = supportFragmentManager.fragments
            var handled: Boolean
            val t = supportFragmentManager.beginTransaction()
            for (f in fragmentList) {
                if(f is LoginFragment) {
                    switchButton.setText(R.string.login_mode_button_login_text)
                    submitButton.setText(R.string.register_button_text)
                    handled = f.onBackPressed()
                    t.remove(mLogin).commit()
                    if(handled) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            add(R.id.login_fragment_bed, mRegister)
                        }
                        break
                    }
                }
                else if(f is RegisterFragment) {
                    switchButton.setText(R.string.login_mode_button_register_text)
                    submitButton.setText(R.string.login_button_text)
                    handled = f.onBackPressed()
                    t.remove(mRegister).commit()
                    if(handled) {
                        commitLogin()
                        break
                    }
                }
            }
        }
        submitButton.setOnClickListener {
            val creator = Intent(this@LoginActivity, CreatorActivity::class.java)
            startActivity(creator)
        }
    }

    private fun commitLogin()
    {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.login_fragment_bed, mLogin)
        }
    }
}
