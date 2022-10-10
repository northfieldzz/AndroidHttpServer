package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.Sensor
import jp.nagano.northfield.sensormonitor.sensor.SensorInterface

class StepDetectorSensor : SensorInterface {
    override val name = Sensor.STRING_TYPE_STEP_DETECTOR
    override val type = Sensor.TYPE_STEP_DETECTOR

    override fun getString(data: FloatArray): String {
        return "Flag: ${data[0] <= 1.0}"
    }
}