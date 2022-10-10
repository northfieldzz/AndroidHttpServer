package jp.nagano.northfield.secretary

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.core.net.toFile
import java.io.*


class MyWaveFile(private val context: Context, private val samplingRate: Int) {
    companion object {
        //wavファイルリフチャンクに書き込むチャンクID用
        private val RIFF = byteArrayOf(
            'R'.code.toByte(),
            'I'.code.toByte(),
            'F'.code.toByte(),
            'F'.code.toByte()
        )

        //WAV形式でRIFFフォーマットを使用する
        private val WAVE = byteArrayOf(
            'W'.code.toByte(),
            'A'.code.toByte(),
            'V'.code.toByte(),
            'E'.code.toByte()
        )

        //fmtチャンク　スペースも必要
        private val FMT_CHUNK = byteArrayOf(
            'f'.code.toByte(),
            'm'.code.toByte(),
            't'.code.toByte(),
            ' '.code.toByte()
        )

        //dataチャンク
        private val DATA_CHUNK = byteArrayOf(
            'd'.code.toByte(),
            'a'.code.toByte(),
            't'.code.toByte(),
            'a'.code.toByte()
        )
    }

    // region
    private var fileSize = 36

    //fmtチャンクのバイト数
    private val fmtSize = 16

    // フォーマットID リニアPCMの場合01 00 2byte
    private val fmtCode = byteArrayOf(1, 0)

    //チャネルカウント モノラルなので1 ステレオなら2にする
    private val channelCount: Short = 1

    //データ速度
    private val bytePerSec = samplingRate * (fmtSize / 8) * channelCount

    //ブロックサイズ (Byte/サンプリングレート * チャンネル数)
    private val blockSize: Short = (fmtSize / 8 * channelCount).toShort()

    //サンプルあたりのビット数 WAVでは8bitか16ビットが選べる
    private val bitPerSample: Short = 16

    //波形データのバイト数
    private var dataSize = 0
    // endregion

    // region File Handler
    private val resolver = context.contentResolver
    private val collection =
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    private val detail = ContentValues().apply {
        put(MediaStore.Audio.Media.DISPLAY_NAME, "sample.wav")
    }
    private val uri = resolver.insert(collection, detail)
    private var recordingFile: File? = null
    private var randomAccessFile: RandomAccessFile? = null

    private var outputStream: FileOutputStream? = null
    private var parcelFileDescriptor: ParcelFileDescriptor? = null

    // endregion

    fun createFile() {
        parcelFileDescriptor = resolver.openFileDescriptor(uri!!, "w", null)!!
        // Write data into the pending audio file.
        recordingFile = File(uri.encodedPath!!)
        randomAccessFile = RandomAccessFile(recordingFile, "rw")
//        randomAccessFile = FileOutputStream(parcelFileDescriptor?.fileDescriptor)
        randomAccessFile!!.seek(0)
        randomAccessFile!!.write(RIFF)
        randomAccessFile!!.write(littleEndianInteger(fileSize))
        randomAccessFile!!.write(WAVE)
        randomAccessFile!!.write(FMT_CHUNK)
        randomAccessFile!!.write(littleEndianInteger(fmtSize))
        randomAccessFile!!.write(fmtCode)
        randomAccessFile!!.write(littleEndianShort(channelCount))
        randomAccessFile!!.write(littleEndianInteger(samplingRate))
        randomAccessFile!!.write(littleEndianInteger(bytePerSec))
        randomAccessFile!!.write(littleEndianShort(blockSize))
        randomAccessFile!!.write(littleEndianShort(bitPerSample))
        randomAccessFile!!.write(DATA_CHUNK)
        randomAccessFile!!.write(littleEndianInteger(dataSize))
    }

    private fun littleEndianInteger(i: Int): ByteArray {
        val buffer = ByteArray(4)
        buffer[0] = i.toByte()
        buffer[1] = (i shr 8).toByte()
        buffer[2] = (i shr 16).toByte()
        buffer[3] = (i shr 24).toByte()
        return buffer
    }

    // PCMデータを追記するメソッド
    fun addBigEndianData(data: ByteArray) {

        // ファイルにデータを追記
        try {
            randomAccessFile!!.seek(randomAccessFile!!.length())
            randomAccessFile!!.write(littleEndianShorts(data))
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        // ファイルサイズを更新
        updateFileSize()

        // データサイズを更新
        updateDataSize()
    }

    // ファイルサイズを更新
    private fun updateFileSize() {
        fileSize = (recordingFile!!.length() - 8).toInt()
        val fileSizeBytes = littleEndianInteger(fileSize)
        try {
            randomAccessFile!!.seek(4L)
            randomAccessFile!!.write(fileSizeBytes)
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    // データサイズを更新
    private fun updateDataSize() {
        dataSize = (recordingFile!!.length() - 44).toInt()
        val dataSizeBytes = littleEndianInteger(dataSize)
        try {
            randomAccessFile!!.seek(40L)
            randomAccessFile!!.write(dataSizeBytes)
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    // short型変数をリトルエンディアンのbyte配列に変更
    private fun littleEndianShort(s: Short): ByteArray {
        val buffer = ByteArray(2)
        buffer[0] = s.toByte()
        buffer[1] = (s.toInt() shr 8).toByte()
        return buffer
    }

    // short型配列をリトルエンディアンのbyte配列に変更
    private fun littleEndianShorts(s: ByteArray): ByteArray {
        val buffer = ByteArray(s.size * 2)
        var i = 0
        while (i < s.size) {
            buffer[2 * i] = s[i]
            buffer[2 * i + 1] = (s[i].toInt() shr 8).toByte()
            i++
        }
        return buffer
    }

    // ファイルを閉じる
    fun close() {
        try {
            outputStream = null
            randomAccessFile?.close()
            parcelFileDescriptor?.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}