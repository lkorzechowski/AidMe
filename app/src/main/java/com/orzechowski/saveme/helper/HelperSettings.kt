package com.orzechowski.saveme.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.orzechowski.saveme.HelperActivity
import com.orzechowski.saveme.R
import com.orzechowski.saveme.volley.BooleanRequest

class HelperSettings(val mActivity: HelperActivity): Fragment()
{
    private lateinit var mQueue: RequestQueue
    private lateinit var mEmail: String
    private val mCallback: ActivityCallback = mActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View
    {
        val view = R.layout.fragment_helper_settings
        mEmail = requireArguments().getString("email") ?: "retrieval error"
        return inflater.inflate(view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val nameEdit = view.findViewById<EditText>(R.id.name_edit)
        val surnameEdit = view.findViewById<EditText>(R.id.surname_edit)
        val titleEdit = view.findViewById<EditText>(R.id.title_edit)
        val professionEdit = view.findViewById<EditText>(R.id.profession_edit)
        val phoneEdit = view.findViewById<EditText>(R.id.phone_edit)
        val submitButton = view.findViewById<Button>(R.id.submit_helper_settings_button)
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(mActivity.cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        mQueue = RequestQueue(cache, network).apply {
            start()
        }
        mQueue.add(JsonObjectRequest(Request.Method.GET, url +
                "fullhelperdetailforemail/" + mEmail, null, { result ->
            var name = result.getString("name")
            var surname = result.getString("surname")
            var title = result.getString("title")
            var profession = result.getString("profession")
            var phone = result.getString("phone")
            nameEdit.setText(name)
            surnameEdit.setText(surname)
            titleEdit.setText(title)
            professionEdit.setText(profession)
            phoneEdit.setText(phone)
            submitButton.setOnClickListener {
                name = nameEdit.text.toString()
                surname = nameEdit.text.toString()
                title = titleEdit.text.toString()
                profession = professionEdit.text.toString()
                phone = phoneEdit.text.toString()
                if(name.length > 2) {
                    if(surname.length > 2) {
                        if(title.isEmpty()) title = "null"
                        if(profession.isEmpty()) profession = "null"
                        if(phone.length < 8) phone = "null"
                        mQueue.add(BooleanRequest(
                            Request.Method.GET,
                            url +
                                    "setfullhelperdetailforemail/" + mEmail + "/" +
                                    result.getString("helperId") + "/" + name + "/" + surname +
                                    "/" + title + "/" + profession + "/" + phone,
                            null,
                            {
                                mCallback.submittedSettings()
                            },
                            {
                                it.printStackTrace()
                            }
                        ))
                    } else {
                        Toast.makeText(mActivity, "Nazwisko jest zbyt krótkie",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(mActivity, "Imię jest zbyt krótkie", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }, {
            it.printStackTrace()
        }))
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy()
    {
        mQueue.stop()
        super.onDestroy()
    }

    interface ActivityCallback
    {
        fun submittedSettings()
    }
}
