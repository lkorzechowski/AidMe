package com.orzechowski.saveme.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.orzechowski.saveme.R
import com.orzechowski.saveme.database.GlobalRoomDatabase
import com.orzechowski.saveme.volley.StringPost

class Report(val mCallback: ActivityCallback) : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View?
    {
        return inflater.inflate(R.layout.fragment_report_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val activity = requireActivity()
        val input = view.findViewById<EditText>(R.id.report_phone_number_input)
        view.findViewById<Button>(R.id.report_submit_button).setOnClickListener {
            val text = input.text.toString()
            if(text.length > 8) {
                val email = GlobalRoomDatabase.getDatabase(activity).emailDAO().get()
                val url = if(email != null) getString(R.string.url) + "report/" + email
                else getString(R.string.url) + "report/n"
                RequestQueue(DiskBasedCache(activity.cacheDir, 1024*1024),
                    BasicNetwork(HurlStack())).apply { start() }.add(StringPost(Request.Method.POST,
                    url, {
                            mCallback.reportSubmitted()
                         }, {
                            if (it.message != null) {
                                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                                Log.e("error", it.message!!)
                            }
                        }).also { it.setRequestBody(text) })
            } else {
                input.error = getString(R.string.number_too_short)
            }
        }
    }

    interface ActivityCallback
    {
        fun reportSubmitted()
    }
}
