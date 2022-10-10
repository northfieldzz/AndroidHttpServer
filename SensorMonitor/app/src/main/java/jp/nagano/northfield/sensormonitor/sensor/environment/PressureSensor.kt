package jp.nagano.northfield.sensormonitor.sensor.environment

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class PressureSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_PRESSURE
    override val type = Sensor.TYPE_PRESSURE

    override fun getString(data: FloatArray): String {
        return "${data[0]}hPa"
    }
}