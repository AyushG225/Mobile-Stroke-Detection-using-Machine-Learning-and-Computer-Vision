package org.tensorflow.lite.examples.classification

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.passedornot
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.speechscore
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.testspassed

@ExperimentalCoroutinesApi
class ScreenAfterSoundActivity : AppCompatActivity(){
    private lateinit var info : TextView
    private lateinit var speechresults : TextView
    private lateinit var emojiresults : ImageView
    private lateinit var moreinfo : Button
    private lateinit var tests_passed : TextView
    private lateinit var myView: View
    private lateinit var inflater : LayoutInflater
    private lateinit var sharedPref : SharedPreferences

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_after_sound)
        info = findViewById(R.id.info)
        speechresults = findViewById(R.id.speechresults)
        emojiresults = findViewById(R.id.emojiresults)
        moreinfo = findViewById(R.id.moreinfo)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        inflater = layoutInflater
        myView = inflater.inflate(R.layout.results, null)
        tests_passed = myView.findViewById(R.id.tests_passed)
        speechscore = intent.getStringExtra("message")?.split(" ")?.last()?.toInt()!!
        if (speechscore > 3){
            emojiresults.setBackgroundResource(R.drawable.smileemoji)
            speechresults.text = "Speech test passed!!!"
            testspassed += 1
            passedornot[1] = 1

            println("TESTSPASSED = $testspassed")
        }
        else{
            emojiresults.setBackgroundResource(R.drawable.sademoji)
            speechresults.text = "Speech test not passed."
        }
        info.text = intent.getStringExtra("message") + "/5"

    }

    @SuppressLint("SetTextI18n")
    fun onClick (v: View) {
        if (v.id == R.id.goback) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (v.id == R.id.showresults) {
            val intent = Intent(this, ResultsActivity::class.java)
            startActivity(intent)
        }
        if (v.id == R.id.startarmsclassification) {
            val intent = Intent(this, ScreenAboutArmsActivity::class.java)
            with (sharedPref.edit()) {
                putString(getString(R.string.tests_passed), tests_passed.text.toString())
                apply()
            }
            startActivity(intent)
        }
        if (v.id == R.id.moreinfo) {
            if (moreinfo.text.equals("More Info")) {
                moreinfo.text = "Less Info"
                info.visibility = View.VISIBLE
            }
            else{
                moreinfo.text = "More Info"
                info.visibility = View.INVISIBLE
            }
        }
    }
}