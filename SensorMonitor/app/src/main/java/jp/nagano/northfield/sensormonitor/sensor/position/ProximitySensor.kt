package jp.nagano.northfield.sensormonitor.sensor.position

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class ProximitySensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_PROXIMITY
    override val type = Sensor.TYPE_PROXIMITY

    override fun getString(data: FloatArray): String {
        return "${data[0]}cm"
    }
}