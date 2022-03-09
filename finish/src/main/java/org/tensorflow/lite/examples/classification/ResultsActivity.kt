package org.tensorflow.lite.examples.classification

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.library.BuildConfig
import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.sendMessage
import com.jessecorbett.diskord.util.withBold
import kotlinx.coroutines.*
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.armsscore
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.passedornot
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.smilescore
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.speechscore
import org.tensorflow.lite.examples.classification.GlobalStoreVariables.Companion.testspassed
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
class ResultsActivity : AppCompatActivity(), CoroutineScope  {
    private var job: Job = Job()
    private lateinit var resultstxt : TextView
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results)
        resultstxt = findViewById(R.id.resultstxt)
        //Note: These codes are placeholders and will not work. A Discord bot has to first be created and then the token has to be used.
        launch {
            val discordBotToken = "DISCORD_BOT_TOKEN"
            val client =
                RestClient.default(discordBotToken)
            val channelId = "CHANNELID"
            val channel = ChannelClient(channelId, client)
            channel.sendMessage("Test Finished")
            var message = ""
            if (testspassed == 3) {
                message = "No need to worry! All tests were passed."
            }
            if (testspassed == 2) {
                message = "Be on the lookout for any more symptoms, as 2 out of 3 tests were passed."
            }
            if (testspassed == 1) {
                message = "Actively monitor symptoms, and call 911 if anything gets worse. Only 1 out of the 3 tests were passed."
            }
            if (testspassed == 0) {
                message = "Stroke Detected. Immediately call 911. None of the tests were passed."

            }
            resultstxt.text = message
            channel.sendMessage(message.withBold())
            message = ""
            message += if (passedornot[0]==1) {
                "Smile Detection passed with a ${smilescore*100}% smiling average."
            } else {
                "Smile Detection failed with a ${smilescore*100}% smiling average."
            }
            message += if (passedornot[1]==1) {
                "\nSpeech Classification passed with a score of $speechscore/5."
            } else {
                "\nSpeech Classification failed with a score of $speechscore/5."
            }
            message += if (passedornot[2]==1) {
                "\nArm Weakness Detection passed with the left arm being raised for ${armsscore[0]} frames and the right arm being raised for ${armsscore[1]} frames."
            } else {
                "\nArm Weakness Detection failed with the left arm being raised for ${armsscore[0]} frames and the right arm being raised for ${armsscore[1]} frames."
            }
            channel.sendMessage(message)
            resultstxt.text = resultstxt.text as String + "\n" + message

        }
    }

}