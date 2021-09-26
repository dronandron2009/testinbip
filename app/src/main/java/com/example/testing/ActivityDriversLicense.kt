package com.example.testing

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.testing.Utils.Companion.CERTIFICATE_REGISTRATION
import com.example.testing.Utils.Companion.DELIVERS_LICENSE
import com.example.testing.Utils.Companion.REGISTRATION_NUMBER

class ActivityDriversLicense: AppCompatActivity(), View.OnClickListener, TextWatcher{

    private var etDeliversLicense: EditText? = null
    private var tvDeliversLicense: TextView? = null
    private var tvWarningDeliversLicense: TextView? = null
    private var tvSkipDeliversLicense: TextView? = null
    private var valueRegistrationNumber: String? = null
    private var valueCertificateRegistration: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivers_license)

        valueRegistrationNumber = intent.getStringExtra(REGISTRATION_NUMBER)
        valueCertificateRegistration = intent.getStringExtra(CERTIFICATE_REGISTRATION)


        etDeliversLicense = findViewById(R.id.et_delivers_license) as EditText
        tvDeliversLicense = findViewById(R.id.bt_delivers_license) as TextView
        tvWarningDeliversLicense = findViewById(R.id.tv_warning_delivers_license) as TextView
        tvSkipDeliversLicense = findViewById(R.id.skip_delivers_license) as TextView

        tvDeliversLicense?.setOnClickListener(this)
        tvSkipDeliversLicense?.setOnClickListener(this)
        etDeliversLicense?.addTextChangedListener(this)
        etDeliversLicense?.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(10))


    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_delivers_license -> {
                tvWarningDeliversLicense?.visibility = View.VISIBLE
                tvWarningDeliversLicense?.text = getString(R.string.warning_input_data_drivers_license)
                etDeliversLicense?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red_ff43)
            }
            R.id.skip_delivers_license ->{
                openSiteDialog()
            }
        }
    }


    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val textDeliversLicense = s.toString()
        etDeliversLicense?.setSelection(textDeliversLicense.length)
        etDeliversLicense?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.green)
        tvWarningDeliversLicense?.visibility = View.GONE
        tvDeliversLicense?.setOnClickListener {
            if (textDeliversLicense.length == 10) {
                val i = Intent(this, ActivitySliderShow::class.java)
                i.putExtra(REGISTRATION_NUMBER, valueRegistrationNumber)
                i.putExtra(CERTIFICATE_REGISTRATION, valueCertificateRegistration)
                i.putExtra(DELIVERS_LICENSE, textDeliversLicense)
                startActivity(i)
            }else if (textDeliversLicense.isNotEmpty()){
                tvWarningDeliversLicense?.visibility = View.VISIBLE
                tvWarningDeliversLicense?.text = getString(R.string.warning_incorrect_data_drivers_license)
                etDeliversLicense?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red_ff43)
            }else{
                tvWarningDeliversLicense?.visibility = View.VISIBLE
                tvWarningDeliversLicense?.text = getString(R.string.warning_input_data_drivers_license)
                etDeliversLicense?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red_ff43)
            }
        }
    }

    private fun openSiteDialog() {
        val aboutDialog: AlertDialog = AlertDialog.Builder(this).setMessage(getString(R.string.text_alertdialog_drivers_license)).setNegativeButton(R.string.alertdialog_input){ dialog, which -> }
            .setPositiveButton(R.string.alertdialog_skip ) { dialog, which ->
                val i = Intent(this, ActivitySliderShow::class.java)
                i.putExtra(REGISTRATION_NUMBER, valueRegistrationNumber)
                i.putExtra(CERTIFICATE_REGISTRATION, valueCertificateRegistration)
                startActivity(i)}.create()
        aboutDialog.show()
        val negativeButton = aboutDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        val positiveButton = aboutDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        negativeButton.setTextColor(ContextCompat.getColorStateList(this, R.color.green_59b))
        positiveButton.setTextColor(ContextCompat.getColorStateList(this, R.color.green_59b))
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }


    override fun afterTextChanged(s: Editable?) {
    }


}