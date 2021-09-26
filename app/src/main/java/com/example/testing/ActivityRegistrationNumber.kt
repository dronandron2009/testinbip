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
import com.example.testing.Utils.Companion.REGISTRATION_NUMBER


class ActivityRegistrationNumber: AppCompatActivity(), View.OnClickListener, TextWatcher{
    private var etRegistrationNumber: EditText? = null
    private var tvRegistrationNumber: TextView? = null
    private var tvWarningRegistrationNumber: TextView? = null
    private var tvSkipRegistrationNumber: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_registration_number)

            etRegistrationNumber = findViewById(R.id.et_registration_numbers) as EditText
            tvWarningRegistrationNumber = findViewById(R.id.tv_warning_registration_number) as TextView
            tvRegistrationNumber = findViewById(R.id.bt_next_registration_number) as TextView
            tvSkipRegistrationNumber = findViewById(R.id.skip_registration_number) as TextView

            tvRegistrationNumber?.setOnClickListener(this)
            tvSkipRegistrationNumber?.setOnClickListener(this)
            etRegistrationNumber?.addTextChangedListener(this)
            etRegistrationNumber?.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(8))



    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_next_registration_number ->{
                    tvWarningRegistrationNumber?.visibility = View.VISIBLE
                    tvWarningRegistrationNumber?.text = getString(R.string.warning_input_data_registration_number)

                    etRegistrationNumber?.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.red_ff43)
            }
            R.id.skip_registration_number -> openSiteDialog()
            }
        }

    private fun openSiteDialog() {
        val aboutDialog: AlertDialog = AlertDialog.Builder(this).setMessage(getString(R.string.text_alertdialog_registration_number)).setNegativeButton(R.string.alertdialog_input){ dialog, which -> }
            .setPositiveButton(R.string.alertdialog_skip) { dialog, which ->
                val i = Intent(this, ActivityCertificateRegistration::class.java)
                startActivity(i)}.create()
        aboutDialog.show()
        val negativeButton = aboutDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        val positiveButton = aboutDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        negativeButton.setTextColor(ContextCompat.getColorStateList(this, R.color.green_59b))
        positiveButton.setTextColor(ContextCompat.getColorStateList(this, R.color.green_59b))
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val textRegistrationNumber = s.toString()
        etRegistrationNumber?.setSelection(textRegistrationNumber.length)
        etRegistrationNumber?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.green)
        tvWarningRegistrationNumber?.visibility = View.GONE
        tvRegistrationNumber?.setOnClickListener {
            if (textRegistrationNumber.length == 8) {
                val i = Intent(this, ActivityCertificateRegistration::class.java)
                i.putExtra(REGISTRATION_NUMBER, textRegistrationNumber)
                startActivity(i)
            }else if (textRegistrationNumber.isNotEmpty()){
                tvWarningRegistrationNumber?.visibility = View.VISIBLE
                tvWarningRegistrationNumber?.text = getString(R.string.warning_incorrect_data_registration_number)
                etRegistrationNumber?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red_ff43)
            }else{
                tvWarningRegistrationNumber?.visibility = View.VISIBLE
                tvWarningRegistrationNumber?.text = getString(R.string.warning_input_data_registration_number)
                etRegistrationNumber?.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.red_ff43)
            }
        }
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
    }
}
