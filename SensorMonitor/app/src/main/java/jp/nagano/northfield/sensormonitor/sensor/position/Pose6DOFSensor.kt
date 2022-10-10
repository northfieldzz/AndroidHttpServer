package jp.nagano.northfield.sensormonitor.sensor.position

import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class Pose6DOFSensor : SensorInterface {
    @RequiresApi(Build.VERSION_CODES.N)
    override val name = Sensor.STRING_TYPE_POSE_6DOF

    @RequiresApi(Build.VERSION_CODES.N)
    override val type = Sensor.TYPE_POSE_6DOF

    override fun getString(data: FloatArray): String {
        return """
        --------
        x*sin(θ/2): ${data[0]}
        y*sin(θ/2): ${data[1]}
        z*sin(θ/2): ${data[2]}
        cos(θ/2): ${data[3]}
        ----Translation along each axis from any origin.----
        X: ${data[4]}
        Y: ${data[5]}
        Z: ${data[6]}
        ----Delta quaternion rotation----
        x*sin(θ/2): ${data[7]}
        y*sin(θ/2): ${data[8]}
        z*sin(θ/2): ${data[9]}
        cos(θ/2): ${data[10]}
        ----Delta translation along each axis----
        X: ${data[11]}
        Y: ${data[12]}
        Z: ${data[13]}
        ----Sequence number----
        ${data[14]}
        """.trimIndent()
    }
}