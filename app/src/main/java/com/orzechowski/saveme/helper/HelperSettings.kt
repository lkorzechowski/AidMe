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
        mEmail = requireArguments().getString("email") ?: " "
        return inflater.inflate(R.layout.fragment_helper_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val nameEdit = view.findViewById<EditText>(R.id.name_edit)
        val surnameEdit = view.findViewById<EditText>(R.id.surname_edit)
        val titleEdit = view.findViewById<EditText>(R.id.title_edit)
        val professionEdit = view.findViewById<EditText>(R.id.profession_edit)
        val phoneEdit = view.findViewById<EditText>(R.id.phone_edit)
        val submitButton = view.findViewById<Button>(R.id.submit_helper_settings_button)
        val url = getString(R.string.url)
        mQueue = RequestQueue(DiskBasedCache(mActivity.cacheDir, 1024*1024),
            BasicNetwork(HurlStack())).apply { start() }
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
            if(phone!="null") phoneEdit.setText(phone)
            submitButton.setOnClickListener {
                name = nameEdit.text.toString()
                surname = nameEdit.text.toString()
                title = titleEdit.text.toString()
                profession = professionEdit.text.toString()
                phone = phoneEdit.text.toString()
                if(name.length > 2) {
                    if(surname.length > 2) {
                        val n = "null"
                        if(title.isEmpty()) title = n
                        if(profession.isEmpty()) profession = n
                        if(phone.length < 8) phone = n
                        mQueue.add(BooleanRequest(
                            Request.Method.POST,
                            url + "setfullhelperdetailforemail/" + mEmail + "/" +
                                    result.getString("helperId") + "/" + name + "/" +
                                    surname + "/" + title + "/" + profession + "/" + phone,
                            null,
                            {
                                mCallback.submittedSettings()
                            },
                            {
                                Toast.makeText(mActivity, it.message, Toast.LENGTH_SHORT).show()
                            }
                        ))
                    } else {
                        Toast.makeText(mActivity, resources.getString(R.string.surname_too_short),
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(mActivity, resources.getString(R.string.name_too_short),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }, {
            Toast.makeText(mActivity, it.message, Toast.LENGTH_SHORT).show()
        }))
        view.findViewById<Button>(R.id.cancel_helper_settings_button).setOnClickListener {
            mCallback.submittedSettings()
        }
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
