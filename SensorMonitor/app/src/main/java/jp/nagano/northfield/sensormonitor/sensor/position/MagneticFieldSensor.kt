package jp.nagano.northfield.sensormonitor.sensor.position

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class MagneticFieldSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_MAGNETIC_FIELD
    override val type = Sensor.TYPE_MAGNETIC_FIELD

    override fun getString(data: FloatArray): String {
        return """
        X: ${data[0]}
        Y: ${data[1]}
        Z: ${data[2]}
        (μT)
        """.trimIndent()
    }
}