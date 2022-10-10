package jp.nagano.northfield.sensormonitor.sensor.environment

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class AmbientTemperatureSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_AMBIENT_TEMPERATURE
    override val type = Sensor.TYPE_AMBIENT_TEMPERATURE

    override fun getString(data: FloatArray): String {
        return "${data[0]}℃"
    }
}