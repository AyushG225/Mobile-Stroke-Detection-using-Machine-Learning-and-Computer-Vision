package org.tensorflow.lite.examples.classification

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class SetContactActivity : AppCompatActivity (){
    private lateinit var number : TextView
    private lateinit var sharedPref : SharedPreferences
    lateinit var contactstatus : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_contact)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        number = findViewById(R.id.setcontact)
        contactstatus = findViewById(R.id.contactstatus)

    }
    @SuppressLint("SetTextI18n")
    fun onClick (v: View) {
        if (v.id == R.id.goback) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (v.id == R.id.save) {
            with (sharedPref.edit()) {
                println("number.text = " + number.text)
                println("getString(R.string.saved_number_key) = " +getString(R.string.saved_number_key))
                putString(getString(R.string.saved_number_key), (number.text.toString()))
                apply()
                println("getString(R.string.saved_number_key) = " +getString(R.string.saved_number_key))
                contactstatus.visibility=VISIBLE
                contactstatus.text="Saved"
                    Timer().schedule(object: TimerTask(){
                    override fun run() {
                        contactstatus.visibility= INVISIBLE
                    }
                }, 1000)
            }
        }
        if (v.id == R.id.clear){
            number = findViewById(R.id.setcontact)
            number.text = ""
            contactstatus.visibility=VISIBLE
            contactstatus.text="Cleared"
            Timer().schedule(object: TimerTask(){
                override fun run() {
                    contactstatus.visibility= INVISIBLE
                }
            }, 1000)
        }
        if (v.id == R.id.retrieve){
            number = findViewById(R.id.setcontact)
            val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
            val defaultValue = resources.getString(R.string.saved_number_default_key)
            val numberstr = sharedPref.getString(getString(R.string.saved_number_key), defaultValue)
            number.text = numberstr
            contactstatus.visibility=VISIBLE
            contactstatus.text="Retrieved"
            Timer().schedule(object: TimerTask(){
                override fun run() {
                    contactstatus.visibility= INVISIBLE
                }
            }, 1000)
        }
    }
}