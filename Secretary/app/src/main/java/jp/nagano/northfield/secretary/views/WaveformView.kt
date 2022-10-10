package jp.nagano.northfield.secretary.views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class WaveformView(
    context: Context,
    attrs: AttributeSet?
) : SurfaceView(context, attrs), SurfaceHolder.Callback, Runnable {
    private val paint by lazy {
        val paint = Paint()
        paint.strokeWidth = 2f
        paint.isAntiAlias = true
        paint.color = Color.GRAY
        paint
    }

    private var buffer: ByteArray? = null
    private var thread: Thread? = null

    init {
        holder.addCallback(this)
        holder.setFormat(PixelFormat.TRANSLUCENT);
    }

    override fun run() {
        try {
            var canvas = holder.lockCanvas()
            canvas.drawColor(Color.WHITE)
            holder.unlockCanvasAndPost(canvas)
            while (thread != null) {
                buffer?.let {
                    canvas = holder.lockCanvas()
                    canvas.drawColor(Color.WHITE)

                    val basePoint = canvas.height / 2f
                    val rate = basePoint / 128
                    var oldX = 0f
                    var oldY = basePoint
                    for ((index, value) in it.withIndex()) {
                        val x = (canvas.width.toFloat() / it.size * index)
                        val y = value.toFloat() * rate + basePoint
                        canvas.drawLine(oldX, oldY, x, y, paint)
                        oldX = x
                        oldY = y
                    }
                    holder.unlockCanvasAndPost(canvas)
                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val canvas = holder.lockCanvas()
        holder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        thread = Thread(this)
        thread?.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread = null
    }

    fun update(buffer: ByteArray, size: Int) {
        this.buffer = buffer.copyOf(size)
    }
}