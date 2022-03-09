package org.tensorflow.lite.examples.classification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ScreenAboutArmsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_about_arms)

    }
    @ExperimentalCoroutinesApi
    fun onClick (v: View){
        if (v.id == R.id.startarmsclassification){
            val intent = Intent(this, ArmsClassificationActivity::class.java)
            startActivity(intent)
        }
    }
}