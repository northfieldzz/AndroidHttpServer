package jp.nagano.northfield.secretary

import android.media.AudioRecord
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.audio.classifier.AudioClassifier


internal class Recording(
    private val audioRecord: AudioRecord,
    private val callback: (ByteArray, Int) -> Unit
) : Thread() {

    private var isOnAir = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun run() {
        super.run()
        val sampling = ByteArray(audioRecord.bufferSizeInFrames)
        audioRecord.startRecording()
//        val startTime = System.currentTimeMillis()
        while (isOnAir) {
            val readSize = audioRecord.read(sampling, 0, sampling.size)
//            audioTensor.load(audioRecord)
//            val output = classifier.classify(audioTensor)
//            val filteredModelOutput = output[0].categories.filter {
//                it.score > MINIMUM_DISPLAY_THRESHOLD
//            }.sortedBy {
//                -it.score
//            }
//            val finishTime = System.currentTimeMillis()
//            Log.d("Interpreter", "Latency = ${finishTime - startTime}ms")
            callback(sampling, readSize)
        }
        audioRecord.stop()
        audioRecord.release()
    }

    override fun start() {
        isOnAir = true
        super.start()
    }

    fun kill() {
        isOnAir = false
    }

    companion object {
        private const val MINIMUM_DISPLAY_THRESHOLD = 0.3f
    }
}


//internal class Recording(
//    private val classifier: AudioClassifier,
//    private val audioRecord: AudioRecord,
//    private val callback: (ArrayList<FrequencyVolume>, List<Category>) -> Unit
//) : Thread() {
//    // FFTのポイント数
//    private val fftSize = 4096
//
//    // デシベルベースラインの設定
//    private var volumeBaseLine = 2.0.pow(15.0) * fftSize * sqrt(2.0)
//
//    // 分解能の計算
//    private var resol = audioRecord.sampleRate / fftSize.toDouble()
//
//    private var isOnAir = false
//
//    private val audioTensor = classifier.createInputTensorAudio()
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun run() {
//        super.run()
//        val sampling = ByteArray(audioRecord.bufferSizeInFrames)
//        audioRecord.startRecording()
//        val startTime = System.currentTimeMillis()
//        while (isOnAir) {
//            audioRecord.read(sampling, 0, sampling.size)
//            val swappedSampling = endianSwapping(sampling)
//
//            //FFTクラスの作成と値の引き渡し
//            val fft = FFT4G(fftSize)
//            val fftData = DoubleArray(fftSize)
//            for (i in 0 until fftSize) {
//                fftData[i] = swappedSampling[i].toDouble()
//            }
//            fft.rdft(1, fftData)
//
//            // デシベルの計算
//            val datasets = ArrayList<FrequencyVolume>()
//            var index = 0
//            while (index < fftSize) {
//                datasets += FrequencyVolume(
//                    (resol * index) / 2,
//                    20 * log10(
//                        sqrt(
//                            fftData[index].pow(2.0) + fftData[index + 1].pow(2.0)
//                        ) / volumeBaseLine
//                    )
//                )
//                index += 2
//            }
//            audioTensor.load(audioRecord)
//            val output = classifier.classify(audioTensor)
//            val filteredModelOutput = output[0].categories.filter {
//                it.score > MINIMUM_DISPLAY_THRESHOLD
//            }.sortedBy {
//                -it.score
//            }
//            val finishTime = System.currentTimeMillis()
//            Log.d("Interpreter", "Latency = ${finishTime - startTime}ms")
//            //音量が最大の周波数と，その音量を表示
//            callback(datasets, filteredModelOutput)
//        }
//        audioRecord.stop()
//        audioRecord.release()
//    }
//
//    /**
//     * エンディアン変換
//     */
//    private fun endianSwapping(original: ByteArray): ShortArray {
//        val byteBuffer = ByteBuffer.wrap(original)
//        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
//        val swapped = ShortArray(original.size)
//        for (i in byteBuffer.position() until byteBuffer.capacity() / 2) {
//            swapped[i] = byteBuffer.short
//        }
//        return swapped
//    }
//
//    override fun start() {
//        isOnAir = true
//        super.start()
//    }
//
//    fun kill() {
//        isOnAir = false
//    }
//
//    companion object {
//        private const val MINIMUM_DISPLAY_THRESHOLD = 0.3f
//    }
//}
//
//class FrequencyVolume(var frequency: Double, var volume: Double) {
//    override fun toString(): String {
//        return "周波数 ${frequency.roundToInt()} : 音量 ${volume.roundToInt()}"
//    }
//}