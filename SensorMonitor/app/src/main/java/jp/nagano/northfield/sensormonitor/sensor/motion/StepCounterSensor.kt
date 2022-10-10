package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class StepCounterSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_STEP_COUNTER
    override val type = Sensor.TYPE_STEP_COUNTER

    override fun getString(data: FloatArray): String {
        return "Count: ${data[0]}"
    }
}