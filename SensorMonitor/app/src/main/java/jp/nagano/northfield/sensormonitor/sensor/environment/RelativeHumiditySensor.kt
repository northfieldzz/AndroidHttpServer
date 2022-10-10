package jp.nagano.northfield.sensormonitor.sensor.environment

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class RelativeHumiditySensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_RELATIVE_HUMIDITY
    override val type = Sensor.TYPE_RELATIVE_HUMIDITY

    override fun getString(data: FloatArray): String {
        return "${data[0]}%"
    }
}