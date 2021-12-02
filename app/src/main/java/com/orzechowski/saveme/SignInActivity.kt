package com.orzechowski.saveme

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.orzechowski.saveme.database.GlobalRoomDatabase
import com.orzechowski.saveme.helper.database.Email
import kotlin.concurrent.thread

class SignInActivity : AppCompatActivity()
{
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val mResultLauncher = registerForActivityResult(ActivityResultContracts
        .StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                progress(account)
            } catch (e: Exception) {
                Toast.makeText(this, "Couldn't sign in", Toast.LENGTH_SHORT)
                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .requestIdToken(getString(R.string.client_id)).build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart()
    {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account != null) progress(account)
        else mResultLauncher.launch(mGoogleSignInClient.signInIntent)
    }

    private fun progress(account: GoogleSignInAccount)
    {
        val email = account.email
        if(email != null) {
            thread {
                GlobalRoomDatabase.getDatabase(this).emailDAO().insert(Email(email))
            }
        }
        val intent = Intent(this@SignInActivity, HelperActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }
}
