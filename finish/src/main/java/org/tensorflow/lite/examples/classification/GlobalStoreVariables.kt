package org.tensorflow.lite.examples.classification

import android.app.Application

class GlobalStoreVariables: Application(){
    companion object {
        var testspassed = 0
        var passedornot = arrayOf(0, 0, 0)
        var smilescore = 0.0f
        var speechscore = 0
        var armsscore = arrayOf(0, 0)
    }

}