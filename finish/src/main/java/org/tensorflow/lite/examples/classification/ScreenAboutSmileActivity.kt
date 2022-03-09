package org.tensorflow.lite.examples.classification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ScreenAboutSmileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_about_smile)

    }
    @ExperimentalCoroutinesApi
    fun onClick (v: View){
        if (v.id == R.id.startsmiledetection){
            val intent = Intent(this, SmileDetectionActivity::class.java)
            startActivity(intent)
        }
    }
}