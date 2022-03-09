package org.tensorflow.lite.examples.classification

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.armsscore
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.incorrectspeechwords
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.passedornot
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.smilescore
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.speechscore
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.testspassed
import java.util.*


@ExperimentalCoroutinesApi
class ScreenAfterSmileActivity : AppCompatActivity(){
    private lateinit var smileresults : TextView
    private lateinit var tests_passed : TextView
    private lateinit var emojiresults : ImageView
    private lateinit var moreinfo : android.widget.Button
    private lateinit var sharedPref : SharedPreferences
    private lateinit var info : TextView
    private lateinit var myView: View
//    lateinit var globalStoreVariables : GlobalStoreVariables.Companion
    private lateinit var inflater : LayoutInflater
    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_after_smile)
        testspassed = 0
        passedornot = arrayOf(0, 0, 0)
        smilescore = 0.0f
        speechscore = 0
        armsscore = arrayOf(0, 0)
        incorrectspeechwords = Vector<String>()
        smileresults = findViewById(R.id.smileresults)
        emojiresults = findViewById(R.id.emojiresults)
        moreinfo = findViewById(R.id.moreinfo)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        inflater = layoutInflater
        myView = inflater.inflate(R.layout.results, null)
        tests_passed = myView.findViewById(R.id.tests_passed)
        info = findViewById(R.id.info)
        val smilingaverage = (intent.getStringExtra("smilingaverage"))
//        val model: TestsPassedViewModel by viewModels()
//        testspassed = GlobalStoreVariables.testspassed
//        testspassed = tests_passed.text.toString().toInt()
//        println("testspassed = " + model.testsPassed)
        val notsmilingaverage = (intent.getStringExtra("notsmilingaverage"))
        if (smilingaverage != null && notsmilingaverage != null) {
            println("Smiling average is " + smilingaverage.toFloat() + "\n" + "not smiling average is " + notsmilingaverage)
            info.text = "Smiling Average Percentage: " + smilingaverage.toFloat()*100+"%" + "\n" + "Not Smiling Average Percentage: " + notsmilingaverage.toFloat()*100+"%"
        }
//        if (smilingaverage != null && notsmilingaverage != null) {
//            smileresults.setText("Smiling Average Percentage: " + smilingaverage.toFloat()*100+"%" + "\n" + "Not Smiling Average Percentage: " + notsmilingaverage.toFloat()*100+"%")
//        }
        if (smilingaverage != null) {
            smilescore = smilingaverage.toFloat()
            if (smilingaverage.toFloat() < 0.6){
                emojiresults.setBackgroundResource(R.drawable.sademoji)
                smileresults.text = "A smile was unfortunately not detected."

            }
            else{
                testspassed += 1
                passedornot[0] = 1
                println("TESTSPASSED = $testspassed")
//                model.incrementTestsPassed()
//
//                model.testsPassed.observe(this, Observer {
//                    showToast(it)
//                })
//                println("testspassed = " + model.testsPassed)
//                tests_passed.setText(Integer.toString(model.testsPassed))
                emojiresults.setBackgroundResource(R.drawable.smileemoji)
                smileresults.text = "A smile was detected!!!"
            }
        }
//        println("testspassed = " + model.testsPassed)
    }

    @SuppressLint("SetTextI18n")
    fun onClick (v: View) {
        if (v.id == R.id.goback) {
            println("in here goback")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (v.id == R.id.startsoundclassification) {
            println("in startsoundclassification")
//            with (sharedPref.edit()) {
//                putString(getString(R.string.tests_passed), tests_passed.text.toString())
//                apply()
//            }
            val intent = Intent(this, ScreenAboutSoundActivity::class.java)
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