package org.tensorflow.lite.examples.classification

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.incorrectspeechwords
import org.tensorflow.lite.task.audio.classifier.AudioClassifier
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import java.io.*

@ExperimentalCoroutinesApi
class SoundClassificationActivity : AppCompatActivity() {
    var TAG = "SoundClassificationActivity"

    var modelPath = "browserfft-speech.tflite"

    var probabilityThreshold: Float = 0.8f

    lateinit var textView: TextView
    lateinit var scoretxt: TextView
    lateinit var randomword: TextView
    private lateinit var changescreen: Intent
    var timer : Timer = Timer()
    private fun killActivity() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sound_classification)


        val REQUEST_RECORD_AUDIO = 1337
        requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO)

        textView = findViewById<TextView>(R.id.output)
        scoretxt = findViewById<TextView>(R.id.scoretxt)
        randomword = findViewById<TextView>(R.id.randomword)
            val recorderSpecsTextView = findViewById<TextView>(R.id.textViewAudioRecorderSpecs)

        val classifier = AudioClassifier.createFromFile(this, modelPath)

        val tensor = classifier.createInputTensorAudio()

        val format = classifier.requiredTensorAudioFormat
        val recorderSpecs = "Number Of Channels: ${format.channels}\n" +
                "Sample Rate: ${format.sampleRate}"
        recorderSpecsTextView.text = recorderSpecs

        //Choosing a Random Word
        val arrayofwordsstr = classifier.classify(tensor)[0].categories.joinToString { it.label }
        var arrayofwords = arrayofwordsstr.split(", ")
        arrayofwords = arrayofwords.minus("background").minus("off")
        var randomWord = arrayofwords[Random().nextInt(arrayofwords.size)]

        println("******************************* " + randomWord + " " + arrayofwords)
        randomword.text = "Current word: " + randomWord

        //score to keep track of how many audio words were guessed correctly
        var score = 0
        var added = 0

        val record = classifier.createAudioRecord()
        record.startRecording()
        var index = 0

        timer.scheduleAtFixedRate(1, 100) {
            index++
            if (index % 30 == 0){
                if (added == 0){
                    incorrectspeechwords.add(randomWord)
                }
                added = 0
                if (index >= 150){
                    changescreen = Intent(this@SoundClassificationActivity, ScreenAfterSoundActivity::class.java)
                    changescreen.putExtra("message", "Final Score: " + score)
                    startActivity(changescreen)
                    timer.cancel()
                }
                arrayofwords = arrayofwords.minus(randomWord)
                randomWord = arrayofwords[Random().nextInt(arrayofwords.size)]
                runOnUiThread {
                    randomword.text = "Current word: " + randomWord
                }
            }
            tensor.load(record)
            val output = classifier.classify(tensor)

            val filteredModelOutput = output[0].categories.filter {
                it.score > probabilityThreshold
            }
            filteredModelOutput.map { it.displayName }.toTypedArray()
            val outputStr =
                filteredModelOutput.sortedBy { -it.score }
                    .joinToString(separator = "\n") { "${it.label} -> ${it.score} " }
            if (outputStr.isNotEmpty()) {
                println("outputStr = " + outputStr.split(" ")[0])
                if (outputStr.split(" ")[0] == randomWord && added == 0) {
                    added = 1
                    score++
                    runOnUiThread {
                        scoretxt.text = "Score: " + score
                    }
                }
                runOnUiThread {
                    textView.text = outputStr
                }
            }
        }
    }
}