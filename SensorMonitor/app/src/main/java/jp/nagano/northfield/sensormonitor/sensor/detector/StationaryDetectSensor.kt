package jp.nagano.northfield.sensormonitor.sensor.detector

import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class StationaryDetectSensor : SensorInterface {
    @RequiresApi(Build.VERSION_CODES.N)
    override val name = Sensor.STRING_TYPE_STATIONARY_DETECT
    @RequiresApi(Build.VERSION_CODES.N)
    override val type = Sensor.TYPE_STATIONARY_DETECT

    override fun getString(data: FloatArray): String {
        return "Flag: ${data[0] <= 1.0}"
    }
}