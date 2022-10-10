package jp.nagano.northfield.sensormonitor.sensor.position

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class MagneticFieldUncalibratedSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED
    override val type = Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED

    override fun getString(data: FloatArray): String {
        return """
        ----Uncalibrated----(μT)
        X: ${data[0]}
        Y: ${data[1]}
        Z: ${data[2]}
        ----bias----(μT)
        X: ${data[3]}
        Y: ${data[4]}
        Z: ${data[5]}
        """.trimIndent()
    }
}