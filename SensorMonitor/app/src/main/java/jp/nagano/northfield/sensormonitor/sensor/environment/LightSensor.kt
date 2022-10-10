package jp.nagano.northfield.sensormonitor.sensor.environment

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class LightSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_LIGHT
    override val type = Sensor.TYPE_LIGHT

    override fun getString(data: FloatArray): String {
        return "${data[0]}lux"
    }
}