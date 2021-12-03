package com.orzechowski.saveme

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.orzechowski.saveme.database.GlobalRoomDatabase
import com.orzechowski.saveme.main.RequestAPI
import kotlin.concurrent.thread

class StartupActivity : AppCompatActivity()
{
    private lateinit var mRequestAPI: RequestAPI

    override fun onCreate(savedInstanceState: Bundle?)
    {
        GlobalRoomDatabase.getDatabase(applicationContext)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_startup)
        mRequestAPI = RequestAPI(this).also {
            it.requestData(cacheDir, getString(R.string.url))
        }
        val componentName = ComponentName(this, AutoUpdater::class.java)
        val info = JobInfo.Builder(444, componentName).setRequiresCharging(false)
            .setPersisted(true).setPeriodic(3600000).build()
        (getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler).schedule(info)
        val progressOne = findViewById<View>(R.id.main_loading_progress_1)
        val progressTwo = findViewById<View>(R.id.main_loading_progress_2)
        val progressThree = findViewById<View>(R.id.main_loading_progress_3)
        thread {
            var timeout = 0
            try {
                while(!mRequestAPI.completed() && timeout < 10) {
                    Thread.sleep(300)
                    runOnUiThread {
                        progressOne.visibility = View.VISIBLE
                    }
                    Thread.sleep(300)
                    runOnUiThread {
                        progressTwo.visibility = View.VISIBLE
                    }
                    Thread.sleep(300)
                    runOnUiThread {
                        progressThree.visibility = View.VISIBLE
                    }
                    Thread.sleep(300)
                    runOnUiThread {
                        progressOne.visibility = View.INVISIBLE
                        progressTwo.visibility = View.INVISIBLE
                        progressThree.visibility = View.INVISIBLE
                    }
                    timeout++
                }
                runOnUiThread {
                    startActivity(Intent(this@StartupActivity,
                        MainActivity::class.java))
                }
            } catch (ignored: InterruptedException) {}
        }
    }
}
