package com.orzechowski.saveme

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

//Bardzo prosta aktywność korzystająca z bibliotek Google w celu zalogowania się na konto użytkownika.
class SignInActivity : AppCompatActivity()
{
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val mResultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
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
            .requestIdToken("1042713587768-2pk21idihc6mbijp2squmr2ekdv65rac.apps" +
                    ".googleusercontent.com").build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart()
    {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account!=null) progress(account)
        else mResultLauncher.launch(mGoogleSignInClient.signInIntent)
    }

    private fun progress(account: GoogleSignInAccount)
    {
        val intent = Intent(this@SignInActivity, HelperActivity::class.java)
        intent.putExtra("email", account.email)
        startActivity(intent)
    }
}
