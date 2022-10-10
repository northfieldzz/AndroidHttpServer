package jp.nagano.northfield.sensormonitor.sensor.environment

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class LightFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_light
    override val informationLabel = R.id.light_info
    override val statusLabel = R.id.light_status
    override val sensor = LightSensor()
    override val tabName: String = "Light"
    override val logTag = "LightSensor"
}