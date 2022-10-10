package jp.nagano.northfield.secretary.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.*


class VisualizerView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    /**
     * Viewの横幅
     */
    private var currentWidth = 0

    /**
     * Viewの高さ
     */
    private var currentHeight = 0

    /**
     * サンプリングレート
     */
    private val samplingRate = 44100

    /**
     * デシベル上限
     */
    private val maxVolume = (128 * sqrt(2.0)).toFloat()

    /**
     * デシベル加減
     */
    private val minVolume = -30f

    /**
     * 最大周波数
     */
    private val maxHz = 30000
    private val maxHzLog = log10(maxHz.toDouble())
    private val maxLogRange = ceil(maxHzLog).toInt()

    /**
     * 最小周波数
     */
    private val minHz = 20
    private val minHzLog = log10(minHz.toDouble())
    private val minLogRange = floor(minHzLog).toInt()

    // 対数の区間あたりの幅 (e.g. 10^1から10^2と，10^2から10^3の描画幅は一緒)
    private val logBlockWidth by lazy { width / (maxHzLog - minHzLog) }

    // X方向の表示オフセット
    private val logOffsetX by lazy { minHzLog * logBlockWidth }

    private val lineNumberX by lazy {
        var num = 10 - (minHz / 10.0.pow(minLogRange)).toInt()
        num += 9 * (maxLogRange - minLogRange - 2)
        num += (maxHz / 10.0.pow(maxLogRange - 1)).toInt()
        num
    }

    // 対数グリッドの座標データ
    private val logGridX = FloatArray(lineNumberX)

    private val lineNumberY = ceil(-minVolume / 10).toInt()
    private val logGridY = FloatArray(lineNumberY)

    private var waveform: ByteArray? = null

    private val waveformPaint by lazy {
        val paint = Paint()
        paint.strokeWidth = 1f
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        paint.shader = fftDataShader
        paint
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        // Viewの高さ，幅が取れるのでそれらに依存した計算を行う
        currentHeight = height
        currentWidth = width
        viewSize()
    }

    override fun onDraw(canvas: Canvas) {
        // Viewのサイズ変更があった場合，再計算
        if (currentWidth != width || currentHeight != height) {
            viewSize()
        }
        drawLogGrid(canvas)
        waveform?.draw(canvas)
    }

    fun update(bytes: ByteArray) {
        waveform = bytes
        postInvalidate()
    }

    // データ表示用のシェーダ
    private val fftDataShader by lazy {
        LinearGradient(
            0f, bottom.toFloat(), 0f, top.toFloat(),
            1, 1,
            Shader.TileMode.CLAMP
        )
    }

    private fun viewSize() {
        // 縦
        var logGridDataCounterX = 0
        for (i in minLogRange until maxLogRange) {
            for (j in 1 until 9) {
                val sample = log10(10.0.pow(i.toDouble()) * j).toFloat()
                val x = sample * logBlockWidth - logOffsetX
                if (x >= left && x <= right) {
                    logGridX[logGridDataCounterX] = x.toFloat()
                    logGridDataCounterX++
                }
            }
        }
        // 横
        for (i in 0 until lineNumberY) {
            logGridY[i] = top + (height / -minVolume * 10) * i
        }
    }

    private val gridPaint by lazy {
        val paint = Paint()
        paint.strokeWidth = 1f
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint
    }

    /**
     * グリッド描画
     */
    private fun drawLogGrid(canvas: Canvas) {
        // 横方向
        for (x in logGridX) {
            canvas.drawLine(x, bottom.toFloat(), x, top.toFloat(), gridPaint)
        }
        // 縦方向
        for (y in logGridY) {
            canvas.drawLine(0f, y, width.toFloat(), y, gridPaint)
        }
    }

    private fun ByteArray.draw(canvas: Canvas) {
        // 直流成分(0番目)は計算しない
        for (i in 1 until size) {
            draw(
                canvas,
                // 注目しているデータの周波数
                i * samplingRate / 2,
                // 振幅スペクトルからデシベル数を計算
                sqrt(this[i * 2].toFloat().pow(2f) + this[i * 2 + 1].toFloat().pow(2f))
            )
        }
    }

    private fun draw(canvas: Canvas, frequency: Int, amplitude: Float) {
        val db = 20.0f * log10(amplitude / maxVolume)
        val x = (log10(frequency.toDouble()) * logBlockWidth - logOffsetX).toFloat()
        if (x >= left && x <= right) {
            val y = top - db / -minVolume * height
            canvas.drawLine(x, bottom.toFloat(), x, y, waveformPaint)
        }
    }
}