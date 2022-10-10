package jp.nagano.northfield.sensormonitor.sensor.environment

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class AmbientTemperatureFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_ambient_temperature
    override val informationLabel = R.id.temperature_info
    override val statusLabel = R.id.temperature_status
    override val sensor = AmbientTemperatureSensor()
    override val logTag = "AmbientTemperatureFragment"
    override val tabName = "Ambient Temperature"
}