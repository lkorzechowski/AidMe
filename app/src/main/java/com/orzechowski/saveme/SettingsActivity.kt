package com.orzechowski.saveme

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.saveme.settings.Contact
import com.orzechowski.saveme.settings.ContactForm
import com.orzechowski.saveme.settings.HelperRecycler
import com.orzechowski.saveme.settings.Policy

//W tej aktywności użytkownik może dostosować ustawienia aplikacji i nie tylko. Znajduje się tu nota
//prawna do wglądu użytkowników, można stąd pobrać zawartość wszystkich poradników, ustawić motyw
//(dostępny również motyw dla użytkowników ze ślepotą barw), wyświetlić listę wszystkich
//wolontariuszy, skontaktować się bezpośrednio z administratorem i wyczyścić pamięć aplikacji.
//Klasy podlegające tej aktywności znajdują się w com.orzechowski.saveme.settings
class SettingsActivity : AppCompatActivity(R.layout.activity_settings), Contact.ActivityCallback
{
    private val mPolicy = Policy()
    private val mHelpers = HelperRecycler()
    private val mContact = Contact(this)
    private val mContactForm = ContactForm()
    private lateinit var mParentLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val policyButton = findViewById<Button>(R.id.policy_button)
        val contributorButton = findViewById<Button>(R.id.contributor_button)
        val downloadAllTutorialsButton = findViewById<Button>(R.id.download_all_tutorials)
        val contactButton = findViewById<Button>(R.id.contact_button)
        val colorBlindButton = findViewById<Button>(R.id.themes_button_colorblind)
        val darkButton = findViewById<Button>(R.id.themes_button_dark)
        mParentLayout = findViewById(R.id.settings_parent_layout)
        policyButton.setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                add(R.id.fragment_layout, mPolicy)
            }
        }
        contributorButton.setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                add(R.id.fragment_layout, mHelpers)
            }
        }
        downloadAllTutorialsButton.setOnClickListener {

        }
        contactButton.setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                add(R.id.fragment_layout, mContact)
            }
        }
        colorBlindButton.setOnClickListener {
            setTheme(R.style.ColorBlind)
            setContentView(R.layout.activity_settings)
        }
        darkButton.setOnClickListener {
            setTheme(R.style.Default)
            setContentView(R.layout.activity_settings)
        }
    }

    override fun onBackPressed()
    {
        mParentLayout.visibility = View.VISIBLE
        val fragmentList: List<*> = supportFragmentManager.fragments
        var handled = false
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        for(f in fragmentList) {
            if(f is Policy) {
                handled = f.onBackPressed()
                t.remove(mPolicy).commit()
                if(handled) break
            }
            else if (f is HelperRecycler) {
                handled = f.onBackPressed()
                t.remove(mHelpers).commit()
                if(handled) break
            }
            else if(f is Contact) {
                handled = f.onBackPressed()
                t.remove(mContact).commit()
                if(handled) break
            }

            else if(f is ContactForm) {
                handled = f.onBackPressed()
                t.remove(mContactForm).commit()
            }
        }
        if(!handled) super.onBackPressed()
    }

    override fun onClick()
    {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.remove(mContact).commit()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_layout, mContactForm)
        }
    }
}
