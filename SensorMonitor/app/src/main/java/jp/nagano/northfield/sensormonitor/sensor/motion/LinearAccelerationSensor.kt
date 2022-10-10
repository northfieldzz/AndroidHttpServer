package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class LinearAccelerationSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_LINEAR_ACCELERATION
    override val type = Sensor.TYPE_LINEAR_ACCELERATION

    override fun getString(data: FloatArray): String {
        return """
        X: ${data[0]}
        Y: ${data[1]}
        Z: ${data[2]}
        (m/s^2)
        """.trimIndent()
    }
}