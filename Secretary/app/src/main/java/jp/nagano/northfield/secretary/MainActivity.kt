package jp.nagano.northfield.secretary

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.CompoundButton
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import jp.nagano.northfield.secretary.views.WaveformView
import java.lang.Integer.max


class MainActivity : AppCompatActivity() {
    /**
     * サンプリングレート (Hz)
     * 全デバイスサポート保障は44100のみ
     */
    private val sampleRate = 44100

    /**
     * フレームレート (fps)
     * 1秒間に何回音声データを処理したいか
     * 各自好きに決める
     */
    private val frameRate = 120
    private val channel = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private lateinit var waveformView: WaveformView
    private lateinit var toggleButton: ToggleButton

    private var mediaRecorder: MediaRecorder? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_CODE_PERMISSION
            )
            return
        } else {
            waveformView = findViewById<WaveformView>(R.id.waveformView)
            toggleButton = findViewById<ToggleButton>(R.id.recordButton)
            toggleButton.setOnCheckedChangeListener(ToggleButtonEvent())
        }
    }

    inner class ToggleButtonEvent : CompoundButton.OnCheckedChangeListener {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (isChecked) {
                start()
            } else {
                stop()
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun start() {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    REQUEST_CODE_PERMISSION
                )
                return
            }

            val detail = ContentValues().apply {
                put(MediaStore.Audio.Media.TITLE, "sample");
                put(MediaStore.Audio.Media.DATE_ADDED, (System.currentTimeMillis() / 1000));
                put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");
            }
            val uri = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, detail)
            val file = uri?.let { contentResolver.openFileDescriptor(it, "w") };

            mediaRecorder = MediaRecorder()
            mediaRecorder?.let {
                it.setAudioSource(MediaRecorder.AudioSource.MIC)
                it.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                it.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                it.setOutputFile(file?.fileDescriptor)
                it.setAudioChannels(1)
                it.prepare()
                it.start()
            }
        }

        private fun stop() {
            mediaRecorder?.stop()
        }

    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
    }
}
