package com.orzechowski.saveme

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.saveme.settings.*

//W tej aktywności użytkownik może dostosować ustawienia aplikacji i nie tylko. Znajduje się tu nota
//prawna do wglądu użytkowników, można stąd pobrać zawartość wszystkich poradników, ustawić motyw
//(dostępny również motyw dla użytkowników ze ślepotą barw), wyświetlić listę wszystkich
//wolontariuszy, skontaktować się bezpośrednio z administratorem i wyczyścić pamięć aplikacji.
//Klasy podlegające tej aktywności znajdują się w com.orzechowski.saveme.settings
class SettingsActivity : AppCompatActivity(R.layout.activity_settings), Report.ActivityCallback
{
    private val mPolicy = Policy()
    private val mHelpers = HelperRecycler()
    private val mContact = Contact()
    private val mReport = Report(this)
    private lateinit var mParentLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mParentLayout = findViewById(R.id.settings_parent_layout)
        findViewById<Button>(R.id.policy_button).setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                add(R.id.fragment_layout, mPolicy)
            }
        }
        findViewById<Button>(R.id.contributor_button).setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                add(R.id.fragment_layout, mHelpers)
            }
        }
        findViewById<Button>(R.id.report_user_button).setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                add(R.id.fragment_layout, mReport)
            }
        }
        findViewById<Button>(R.id.contact_button).setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                add(R.id.fragment_layout, mContact)
            }
        }
        findViewById<Button>(R.id.themes_button_colorblind).setOnClickListener {
            setTheme(R.style.ColorBlind)
            setContentView(R.layout.activity_settings)
        }
        findViewById<Button>(R.id.themes_button_dark).setOnClickListener {
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
                handled = true
                t.remove(mPolicy).commit()
                if(handled) break
            }
            else if (f is HelperRecycler) {
                handled = true
                t.remove(mHelpers).commit()
                if(handled) break
            }
            else if(f is Contact) {
                handled = true
                t.remove(mContact).commit()
                if(handled) break
            }
            else if(f is Report) {
                handled = true
                t.remove(mReport).commit()
            }
        }
        if(!handled) super.onBackPressed()
    }

    override fun reportSubmitted()
    {
        supportFragmentManager.beginTransaction().remove(mReport).commit()
        mParentLayout.visibility = View.VISIBLE
        Toast.makeText(this, getString(R.string.report_submitted_toast), Toast.LENGTH_SHORT)
            .show()
    }
}
