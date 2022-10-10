package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface


@RequiresApi(Build.VERSION_CODES.O)
class AccelerometerUncalibratedSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_ACCELEROMETER_UNCALIBRATED
    override val type = Sensor.TYPE_ACCELEROMETER_UNCALIBRATED

    override fun getString(data: FloatArray): String {
        return """
        ----Uncalibrated----(m/s^2)
        X: ${data[0]}
        Y: ${data[1]}
        Z: ${data[2]}
        ----estimated bias----(m/s^2)
        X: ${data[3]}
        Y: ${data[4]}
        Z: ${data[5]}
        """.trimIndent()
    }
}
