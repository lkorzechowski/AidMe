package com.orzechowski.prototyp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.orzechowski.prototyp.poradnikrecycler.ListAdapter
import com.orzechowski.prototyp.poradnikrecycler.Recycler

class PoradnikMasazSerca : AppCompatActivity(R.layout.activity_masaz_serca), ListAdapter.WybranoTytul{
    private val bundle = Bundle()
    private lateinit var wersjaPelna : Button
    private lateinit var wersjaBezPodstaw : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        bundle.putStringArray("tytuly", resources.getStringArray(R.array.masaz_serca_instrukcje_tytuly))
        bundle.putStringArray("instrukcje", resources.getStringArray(R.array.masaz_serca_instrukcje_instrukcje))
        pickVersion()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        wersjaPelna.visibility = View.VISIBLE
        wersjaBezPodstaw.visibility = View.VISIBLE
    }

    fun pickVersion(){
        wersjaPelna = findViewById(R.id.masaz_podstawowe_kroki)
        wersjaBezPodstaw = findViewById(R.id.masaz_tylko_tempo)

        wersjaPelna.setOnClickListener{
            bundle.putIntArray("wersja", resources.getIntArray(R.array.masaz_wersja_1))
            bundle.putIntArray("czas", resources.getIntArray(R.array.masaz_wersja_1_czas))
            initialize()
        }
        wersjaBezPodstaw.setOnClickListener {
            bundle.putIntArray("wersja", resources.getIntArray(R.array.masaz_wersja_2))
            bundle.putIntArray("czas", resources.getIntArray(R.array.masaz_wersja_2_czas))
            initialize()
        }
    }

    fun initialize(){
        wersjaPelna.visibility = View.GONE
        wersjaBezPodstaw.visibility = View.GONE
        val cprVideo = findViewById<VideoView>(R.id.cpr_video_embed)
        cprVideo.setVideoPath("android.resource://" + packageName + "/" + R.raw.cpr_video)
        cprVideo.setOnCompletionListener { cprVideo.start() }
        cprVideo.start()
        getRecycler()
    }

    fun getRecycler(){
        val myObj = Recycler()
        myObj.arguments = bundle
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<Recycler>(R.id.layout_instrukcje, args = bundle)
        }
    }
    override fun onClick(position: Int) {
        //nagraniadzwiekowe.get(position) --- potem

    }
}