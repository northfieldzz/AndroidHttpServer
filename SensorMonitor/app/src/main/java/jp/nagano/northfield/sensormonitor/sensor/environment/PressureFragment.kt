package jp.nagano.northfield.sensormonitor.sensor.environment

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class PressureFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_pressure
    override val informationLabel = R.id.pressure_info
    override val statusLabel = R.id.pressure_status
    override val sensor = PressureSensor()
    override val logTag = "PressureFragment"
    override val tabName = "Pressure"
}