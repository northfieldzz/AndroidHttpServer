package jp.nagano.northfield.sensormonitor.sensor.motion

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class GravityFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_gravity
    override val informationLabel = R.id.gravity_info
    override val statusLabel = R.id.gravity_status
    override val sensor = GravitySensor()
    override val logTag = "GravityFragment"
    override val tabName = "Gravity"
}

