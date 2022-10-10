package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class GyroscopeSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_GYROSCOPE
    override val type = Sensor.TYPE_GYROSCOPE

    override fun getString(data: FloatArray): String {
        return """
            X: ${data[0]}
            Y: ${data[1]}
            Z: ${data[2]}
            (rad/s)
            """.trimIndent()
    }
}