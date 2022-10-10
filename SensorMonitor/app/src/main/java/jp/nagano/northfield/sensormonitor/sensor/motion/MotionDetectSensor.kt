package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class MotionDetectSensor : SensorInterface {
    @RequiresApi(Build.VERSION_CODES.N)
    override val name = Sensor.STRING_TYPE_MOTION_DETECT

    @RequiresApi(Build.VERSION_CODES.N)
    override val type = Sensor.TYPE_MOTION_DETECT

    override fun getString(data: FloatArray): String {
        return "Flag: ${data[0] <= 1.0}"
    }
}