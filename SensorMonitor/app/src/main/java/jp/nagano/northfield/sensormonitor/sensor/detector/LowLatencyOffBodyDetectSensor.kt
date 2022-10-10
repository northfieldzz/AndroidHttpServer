package jp.nagano.northfield.sensormonitor.sensor.detector

import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class LowLatencyOffBodyDetectSensor : SensorInterface {
    @RequiresApi(Build.VERSION_CODES.O)
    override val name = Sensor.STRING_TYPE_LOW_LATENCY_OFFBODY_DETECT

    @RequiresApi(Build.VERSION_CODES.O)
    override val type = Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT

    override fun getString(data: FloatArray): String {
        return "Flag: ${data[0] <= 1.0}"
    }
}