package org.tensorflow.lite.examples.classification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ScreenAboutSoundActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_about_sound)

    }
    @ExperimentalCoroutinesApi
    fun onClick (v: View){
        if (v.id == R.id.startsoundclassification){
            val intent = Intent(this, SoundClassificationActivity::class.java)
            startActivity(intent)
        }
    }
}