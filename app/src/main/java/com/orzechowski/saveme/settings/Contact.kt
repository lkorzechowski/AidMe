package com.orzechowski.saveme.settings

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.orzechowski.saveme.R
import android.widget.Toast

class Contact : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View?
    {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        view.findViewById<Button>(R.id.contact_write_email_button).setOnClickListener {
            val messageId = (Math.random() * 10000).toInt().toString()
            (getString(R.string.request_code_info) + messageId).also {
                view.findViewById<TextView>(R.id.contact_info).text = it }
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:")
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail)))
            intent.putExtra(Intent.EXTRA_TEXT, view.findViewById<EditText>(R.id.contact_input).text
                .toString())
            intent.putExtra(Intent.EXTRA_SUBJECT, messageId)
            try {
                startActivity(Intent.createChooser(intent,
                    getString(R.string.email_chooser_header)))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(requireContext(), getString(R.string.client_email_error),
                    Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<TextView>(R.id.pollub_signature).text =
                resources.getString(R.string.pollub_signature) + "\n" +
                resources.getString(R.string.promotor) + "\n" +
                resources.getString(R.string.me)
    }
}
