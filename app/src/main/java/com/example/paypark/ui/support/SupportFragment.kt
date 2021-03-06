package com.example.paypark.ui.support

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.paypark.R
import com.example.paypark.managers.SharedPreferencesManager

class SupportFragment : Fragment(), View.OnClickListener {

    lateinit var btnCall: Button
    lateinit var btnEmail: Button
    val REQUEST_CALL = 101

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_support, container, false)

        btnCall = root.findViewById(R.id.btnCall)
        btnCall.setOnClickListener(this)

        btnEmail = root.findViewById(R.id.btnEmail)
        btnEmail.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCall -> {
                this.makeCall()
            }
            R.id.btnEmail -> {
                this.sendEmail()
            }
        }
    }

    private fun makeCall() {
        val phoneNumber = "1231231234"

        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:" + phoneNumber)
        }

        if (checkPermission()) {
            if (callIntent.resolveActivity(this.requireActivity().packageManager) != null) {
                startActivity(callIntent)
            }
        } else {
            this.requestPermission()
        }
    }

    private fun sendEmail() {
        val user_email = SharedPreferencesManager.read(SharedPreferencesManager.EMAIL, "").toString()

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") //to indicate only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("villadia@sheridancollege.ca"))
            putExtra(Intent.EXTRA_SUBJECT, "Support Request by " + user_email)
        }

        if (emailIntent.resolveActivity(this.requireActivity().packageManager) != null) {
            startActivity(emailIntent)
        }
    }

    private fun checkPermission() : Boolean {
        return(ContextCompat.checkSelfPermission(requireActivity().applicationContext, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),
            REQUEST_CALL)
    }
}