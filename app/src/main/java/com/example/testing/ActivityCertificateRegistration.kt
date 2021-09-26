package com.example.testing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.testing.Utils.Companion.CERTIFICATE_REGISTRATION
import com.example.testing.Utils.Companion.REGISTRATION_NUMBER


class ActivityCertificateRegistration: AppCompatActivity(), View.OnClickListener, TextWatcher{

    private var etCertificateRegistration: EditText? = null
    private var tvCertificateRegistration: TextView? = null
    private var tvWarningCertificateRegistration: TextView? = null
    private var tvSkipCertificateRegistration: TextView? = null
    private var valueRegistrationNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate_registration)

        etCertificateRegistration = findViewById(R.id.et_certificate_registration)
        tvCertificateRegistration = findViewById(R.id.bt_next_certificate_registration)
        tvWarningCertificateRegistration = findViewById(R.id.tv_warning_certificate_registration)
        tvSkipCertificateRegistration = findViewById(R.id.tv_skip_certificate_registration)

        tvCertificateRegistration?.setOnClickListener(this)
        tvSkipCertificateRegistration?.setOnClickListener(this)
        etCertificateRegistration?.addTextChangedListener(this)
        etCertificateRegistration?.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(10))

        valueRegistrationNumber = intent.getStringExtra(REGISTRATION_NUMBER)

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }


    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val textCertificateRegistration = s.toString()
        etCertificateRegistration?.setSelection(textCertificateRegistration.length)
        etCertificateRegistration?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.green)
        tvWarningCertificateRegistration?.visibility = View.GONE
        tvCertificateRegistration?.setOnClickListener {
                if (textCertificateRegistration.length == 10) {
                    val i = Intent(this, ActivityDriversLicense::class.java)
                    i.putExtra(CERTIFICATE_REGISTRATION, textCertificateRegistration)
                    i.putExtra(REGISTRATION_NUMBER, valueRegistrationNumber)
                    startActivity(i)
                }else if (textCertificateRegistration.isNotEmpty()){
                    tvWarningCertificateRegistration?.visibility = View.VISIBLE
                    tvWarningCertificateRegistration?.text = getString(R.string.warning_incorrect_data_certificate_number)
                    etCertificateRegistration?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red_ff43)
                }else{
                    tvWarningCertificateRegistration?.visibility = View.VISIBLE
                    tvWarningCertificateRegistration?.text = getString(R.string.warning_input_data_certificate_number)
                    etCertificateRegistration?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red_ff43)
                }
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_next_certificate_registration -> {
                tvWarningCertificateRegistration?.visibility = View.VISIBLE
                tvWarningCertificateRegistration?.text = getString(R.string.warning_input_data_certificate_number)
                etCertificateRegistration?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red_ff43)
            }
            R.id.tv_skip_certificate_registration ->{
                openSiteDialog()
            }
        }
    }

    private fun openSiteDialog() {
        val aboutDialog: AlertDialog = AlertDialog.Builder(this).setMessage(getString(R.string.text_alertdialog_certificate_registration)).setNegativeButton(R.string.alertdialog_input){ dialog, which -> }
            .setPositiveButton(R.string.alertdialog_skip) { dialog, which ->
                val i = Intent(this, ActivityDriversLicense::class.java)
                i.putExtra(REGISTRATION_NUMBER, valueRegistrationNumber)
                startActivity(i)}.create()
        aboutDialog.show()
        val negativeButton = aboutDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        val positiveButton = aboutDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        negativeButton.setTextColor(ContextCompat.getColorStateList(this, R.color.green_59b))
        positiveButton.setTextColor(ContextCompat.getColorStateList(this, R.color.green_59b))
    }

}
