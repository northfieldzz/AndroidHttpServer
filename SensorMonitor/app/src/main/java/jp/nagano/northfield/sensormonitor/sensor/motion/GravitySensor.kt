package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class GravitySensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_GRAVITY
    override val type = Sensor.TYPE_GRAVITY

    override fun getString(data: FloatArray): String {
        return """
        X: ${data[0]}
        Y: ${data[1]}
        Z: ${data[2]}
        m/s^2
        """.trimIndent()
    }
}