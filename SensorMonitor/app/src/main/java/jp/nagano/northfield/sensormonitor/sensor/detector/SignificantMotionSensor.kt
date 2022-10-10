package jp.nagano.northfield.sensormonitor.sensor.detector

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class SignificantMotionSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_SIGNIFICANT_MOTION
    override val type = Sensor.TYPE_SIGNIFICANT_MOTION

    override fun getString(data: FloatArray): String {
        return "Flag: ${data[0] <= 1.0}"
    }
}