package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class GyroscopeUncalibratedSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_GYROSCOPE_UNCALIBRATED
    override val type = Sensor.TYPE_GYROSCOPE_UNCALIBRATED

    override fun getString(data: FloatArray): String {
        return """
            ---- ----(rad/s\n)
            X: ${data[0]}
            Y: ${data[1]}
            Z: ${data[2]}
            ---- ----(rad/s\n)
            X: ${data[3]}
            Y: ${data[4]}
            Z: ${data[5]}
            """.trimIndent()
    }
}