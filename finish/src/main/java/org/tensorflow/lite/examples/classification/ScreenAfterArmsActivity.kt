package org.tensorflow.lite.examples.classification

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.armsscore
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.passedornot
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.testspassed

@ExperimentalCoroutinesApi
class ScreenAfterArmsActivity : AppCompatActivity() {
    private lateinit var info : TextView
    private lateinit var armsresults : TextView
    private lateinit var emojiresults : ImageView
    private lateinit var sharedPref : SharedPreferences
    private lateinit var moreinfo : android.widget.Button
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_after_arms)
        info = findViewById(R.id.info)
        armsresults = findViewById(R.id.armsresults)
        emojiresults = findViewById(R.id.emojiresults)
        moreinfo = findViewById(R.id.moreinfo)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        val leftcnt = intent.getStringExtra("leftcnt")?.toInt()
        val rightcnt = intent.getStringExtra("rightcnt")?.toInt()
        if (leftcnt != null && rightcnt != null) {
            if (leftcnt > 2 && rightcnt > 2){
                passedornot[2] = 1
                emojiresults.setBackgroundResource(R.drawable.smileemoji)
                armsresults.text = "Arms test passed!!!"
                testspassed += 1
            }
            else{
                emojiresults.setBackgroundResource(R.drawable.sademoji)
                armsresults.text = "Arms test failed."
            }
            armsscore[0] = leftcnt
            armsscore[1] = rightcnt
        }
        info.text = "Left arm raised for $leftcnt frames\nRight arm raised for $rightcnt frames"

    }
    @SuppressLint("SetTextI18n")
    fun onClick (v: View){
        if (v.id == R.id.goback) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (v.id == R.id.showresults) {
            val intent = Intent(this, ResultsActivity::class.java)
            with (sharedPref.edit()) {
                putString(getString(R.string.tests_passed), testspassed.toString())
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