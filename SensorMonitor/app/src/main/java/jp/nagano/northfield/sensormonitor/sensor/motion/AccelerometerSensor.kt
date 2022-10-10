package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class AccelerometerSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_ACCELEROMETER
    override val type = Sensor.TYPE_ACCELEROMETER

    override fun getString(data: FloatArray): String {
        return """
        X: ${data[0]}
        Y: ${data[1]}
        Z: ${data[2]}
        (m/s^2)
        """.trimIndent()
    }
}