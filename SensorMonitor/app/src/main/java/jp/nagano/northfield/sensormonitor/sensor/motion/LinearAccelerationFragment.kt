package jp.nagano.northfield.sensormonitor.sensor.motion

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class LinearAccelerationFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_linear_acceleration
    override val informationLabel = R.id.linear_acceleration_info
    override val statusLabel = R.id.linear_acceleration_status
    override val sensor = LinearAccelerationSensor()
    override val logTag = "LinearAccelerationFragment"
    override val tabName = "Linear Acceleration"
}