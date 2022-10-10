package jp.nagano.northfield.secretary

import android.app.Activity
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.audio.classifier.AudioClassifier

class Interpreter(private val context: Context, private val callback: (List<Category>) -> Unit) {
    private val classifier: AudioClassifier? = null


    @RequiresApi(Build.VERSION_CODES.M)
    private fun startAudioClassification() {
        val classifier = AudioClassifier.createFromFile(context, MODEL_FILE)
        val audioTensor = classifier.createInputTensorAudio()

        val run = Runnable {
            val startTime = System.currentTimeMillis()

            val record = classifier.createAudioRecord()
            record.startRecording()
            audioTensor.load(record)
            val output = classifier.classify(audioTensor)
            val filteredModelOutput = output[0].categories.filter {
                it.score > MINIMUM_DISPLAY_THRESHOLD
            }.sortedBy {
                -it.score
            }

            val finishTime = System.currentTimeMillis()

            Log.d("Interpreter", "Latency = ${finishTime - startTime}ms")

            callback(filteredModelOutput)
        }

    }

    companion object {
        private const val MODEL_FILE = "yamnet.tflite"
        private const val MINIMUM_DISPLAY_THRESHOLD = 0.3f
    }
}