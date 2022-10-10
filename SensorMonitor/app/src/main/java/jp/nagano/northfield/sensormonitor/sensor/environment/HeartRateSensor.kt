package jp.nagano.northfield.sensormonitor.sensor.environment

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class HeartRateSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_HEART_RATE
    override val type = Sensor.TYPE_HEART_RATE

    override fun getString(data: FloatArray): String {
        return "Flag: ${data[0] <= 1.0}"
    }
}
