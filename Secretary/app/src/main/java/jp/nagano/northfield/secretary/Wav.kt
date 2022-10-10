//package jp.nagano.northfield.secretary
//
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import java.lang.Exception
//
//class Wav(
//    wavFile: File,
//    private val sampleRate: Int,
//    private val channels: Int,
//    bitNum: Int
//) {
//    private val byteRate = (sampleRate * channels * bitNum / 8).toLong()
//    private var out = FileOutputStream(wavFile)
//
//    init {
//        val totalAudioLen = `in`.channel.size()
//        val totalDataLen = totalAudioLen + 36
//        writeWaveFileHeader(out, totalAudioLen, totalDataLen, sampleRate, channels, byteRate)
//    }
//
//
//    fun fromPcm(
//        inPcmFilePath: File,
//        outWavFilePath: File,
//        sampleRate: Int,
//        channels: Int,
//        bitNum: Int
//    ) {
//        val `in`: FileInputStream?
//        val out: FileOutputStream?
//        val data = ByteArray(1024)
//        try {
//            val byteRate = (sampleRate * channels * bitNum / 8).toLong()
//            `in` = FileInputStream(inPcmFilePath)
//            out = FileOutputStream(outWavFilePath)
//            val totalAudioLen = `in`.channel.size()
//            val totalDataLen = totalAudioLen + 36
//            writeWaveFileHeader(out, totalAudioLen, totalDataLen, sampleRate, channels, byteRate)
//            var length = 0
//            while (`in`.read(data).also { length = it } > 0) {
//                out.write(data, 0, length)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
////            IOUtil.close(`in`, out)
//        }
//    }
//
//    fun save() {
//
//    }
//
//    private fun writeWaveFileHeader(
//        out: FileOutputStream,
//        totalAudioLen: Long,
//        totalDataLen: Long,
//        sampleRate: Int,
//        channels: Int,
//        byteRate: Long
//    ) {
//        val header = ByteArray(44)
//        header[0] = 'R'.code.toByte()
//        header[1] = 'I'.code.toByte()
//        header[2] = 'F'.code.toByte()
//        header[3] = 'F'.code.toByte()
//        header[4] = (totalDataLen and 0xff).toByte()
//        header[5] = (totalDataLen shr 8 and 0xff).toByte()
//        header[6] = (totalDataLen shr 16 and 0xff).toByte()
//        header[7] = (totalDataLen shr 24 and 0xff).toByte()
//        header[8] = 'W'.code.toByte()
//        header[9] = 'A'.code.toByte()
//        header[10] = 'V'.code.toByte()
//        header[11] = 'E'.code.toByte()
//
//        header[12] = 'f'.code.toByte()
//        header[13] = 'm'.code.toByte()
//        header[14] = 't'.code.toByte()
//        header[15] = ' '.code.toByte()
//
//        header[16] = 16
//        header[17] = 0
//        header[18] = 0
//        header[19] = 0
//
//        header[20] = 1
//        header[21] = 0
//
//        header[22] = channels.toByte()
//        header[23] = 0
//
//        header[24] = (sampleRate and 0xff).toByte()
//        header[25] = (sampleRate shr 8 and 0xff).toByte()
//        header[26] = (sampleRate shr 16 and 0xff).toByte()
//        header[27] = (sampleRate shr 24 and 0xff).toByte()
//
//        header[28] = (byteRate and 0xff).toByte()
//        header[29] = (byteRate shr 8 and 0xff).toByte()
//        header[30] = (byteRate shr 16 and 0xff).toByte()
//        header[31] = (byteRate shr 24 and 0xff).toByte()
//
//        header[32] = (channels * 16 / 8).toByte()
//        header[33] = 0
//
//        header[34] = 16
//        header[35] = 0
//
//        header[36] = 'd'.code.toByte()
//        header[37] = 'a'.code.toByte()
//        header[38] = 't'.code.toByte()
//        header[39] = 'a'.code.toByte()
//        header[40] = (totalAudioLen and 0xff).toByte()
//        header[41] = (totalAudioLen shr 8 and 0xff).toByte()
//        header[42] = (totalAudioLen shr 16 and 0xff).toByte()
//        header[43] = (totalAudioLen shr 24 and 0xff).toByte()
//        out.write(header, 0, 44)
//    }
//}