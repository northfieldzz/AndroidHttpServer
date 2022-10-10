package jp.nagano.northfield.sensormonitor.sensor.environment

import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class HeartBeatSensor : SensorInterface {
    @RequiresApi(Build.VERSION_CODES.N)
    override val name = Sensor.STRING_TYPE_HEART_BEAT

    @RequiresApi(Build.VERSION_CODES.N)
    override val type = Sensor.TYPE_HEART_BEAT

    override fun getString(data: FloatArray): String {
        return "Confidence: ${data[0]}"
    }
}