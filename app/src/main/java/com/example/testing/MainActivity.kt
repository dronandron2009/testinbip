package com.example.testing

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import com.example.testing.Utils.Companion.CERTIFICATE_REGISTRATION
import com.example.testing.Utils.Companion.REGISTRATION_NUMBER
import android.content.Context
import android.text.InputFilter
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.testing.Utils.Companion.CHECK
import com.example.testing.Utils.Companion.DELIVERS_LICENSE


class MainActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    private var etRegistrationNumber: EditText? = null
    private var etCertificateRegistration: EditText? = null
    private var etDriversLicense: EditText? = null
    private var btSave: Button? = null
    private var getPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var valueRegistrationNumber: String? = null
    private var valueCertificateRegistration: String? = null
    private var valueDeliversLicense: String? = null
    private var check: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPreferences = getPreferences(Activity.MODE_PRIVATE)
        editor = getPreferences?.edit()
        valueRegistrationNumber = intent.extras?.getString(REGISTRATION_NUMBER)
        valueCertificateRegistration = intent.extras?.getString(CERTIFICATE_REGISTRATION)
        valueDeliversLicense = intent.extras?.getString(DELIVERS_LICENSE)
        check = intent.extras?.getBoolean(CHECK)
        if (getPreferences?.all?.size == 0  && check == null || getPreferences?.all?.size == 0 && check == false) {
            val i = Intent(this, ActivityRegistrationNumber::class.java)
            startActivity(i)
        }else {
            setContentView(R.layout.activity_main)
        }


        etRegistrationNumber = findViewById(R.id.et_registration_numbers_to_main)
        etCertificateRegistration = findViewById(R.id.et_certificate_registration_to_main)
        etDriversLicense = findViewById(R.id.et_drivers_license_main)
        btSave = findViewById(R.id.save_main_activity)
        btSave?.setOnClickListener(this)
        etRegistrationNumber?.addTextChangedListener(this)
        etCertificateRegistration?.addTextChangedListener(this)
        etDriversLicense?.addTextChangedListener(this)
        etRegistrationNumber?.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(8))
        etCertificateRegistration?.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(10))
        etDriversLicense?.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(10))

        if (valueRegistrationNumber?.isNotEmpty() == true ||valueCertificateRegistration?.isNotEmpty() == true ||  valueDeliversLicense?.isNotEmpty() == true) {
            editor?.putString(REGISTRATION_NUMBER, valueRegistrationNumber)
            editor?.putString(CERTIFICATE_REGISTRATION, valueCertificateRegistration)
            editor?.putString(DELIVERS_LICENSE, valueDeliversLicense)
            editor?.apply()
        }

        if (getPreferences?.all?.size!! > 0) {
            etRegistrationNumber?.setText(getPreferences?.getString(REGISTRATION_NUMBER, valueRegistrationNumber))
        }
        if (getPreferences?.all?.size!! > 0) {
            etCertificateRegistration?.setText(getPreferences?.getString(CERTIFICATE_REGISTRATION, valueCertificateRegistration))
        }
        if (getPreferences?.all?.size!! > 0){
            etDriversLicense?.setText(getPreferences?.getString(DELIVERS_LICENSE, valueDeliversLicense))
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.save_main_activity -> {
                valueRegistrationNumber = etRegistrationNumber?.editableText.toString()
                valueCertificateRegistration = etCertificateRegistration?.editableText.toString()
                valueDeliversLicense = etDriversLicense?.editableText.toString()
                editor?.putString(REGISTRATION_NUMBER, valueRegistrationNumber)
                editor?.putString(CERTIFICATE_REGISTRATION, valueCertificateRegistration)
                editor?.putString(DELIVERS_LICENSE, valueDeliversLicense)
                editor?.apply()
                btSave?.visibility = View.GONE
                this.currentFocus?.let { view ->
                    val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    keyboard?.hideSoftInputFromWindow(view.windowToken, 0)
                }
                val toast = Toast.makeText(this, R.string.save_main, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (s?.isNotEmpty() == true) btSave?.visibility = View.VISIBLE else btSave?.visibility = View.GONE
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
    }

}