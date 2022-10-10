package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class RotationVectorSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_ROTATION_VECTOR
    override val type = Sensor.TYPE_ROTATION_VECTOR

    override fun getString(data: FloatArray): String {

        return if (data.count() == 5) {
            """
            x*sin(θ/2): ${data[0]} 
            y*sin(θ/2): ${data[1]} 
            z*sin(θ/2): ${data[2]}
            cos(θ/2)  : ${data[3]}
            accuracy  : ${data[4]}
            """.trimIndent()
        } else {
            """
            x*sin(θ/2): ${data[0]}                 
            y*sin(θ/2): ${data[1]}                 
            z*sin(θ/2): ${data[2]}
            accuracy  : ${data[3]}
            """.trimIndent()
        }
    }
}