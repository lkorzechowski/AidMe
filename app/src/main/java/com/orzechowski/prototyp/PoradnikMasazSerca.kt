package com.orzechowski.prototyp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.orzechowski.prototyp.poradnikrecycler.ListAdapter
import com.orzechowski.prototyp.poradnikrecycler.Recycler

class PoradnikMasazSerca : AppCompatActivity(R.layout.activity_poradnik), ListAdapter.WybranoTytul{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val tytuly = resources.getStringArray(R.array.instrukcje_tytuly)
        val instrukcje = resources.getStringArray(R.array.instrukcje_instrukcje)
        val bundle = Bundle()
        bundle.putStringArray("tytuly", tytuly)
        bundle.putStringArray("instrukcje", instrukcje)
        val myObj = Recycler()
        myObj.arguments = bundle
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Recycler>(R.id.layout_instrukcje, args = bundle)
                //val widokRecyclera = findViewById<View>(R.id.layout_instrukcje)
                //val widokRzedu = widokRecyclera.findViewWithTag<View>(1)
            }
        }
    }

    override fun onClick(position: Int) {
        //nagraniadzwiekowe.get(position) --- potem

    }
}