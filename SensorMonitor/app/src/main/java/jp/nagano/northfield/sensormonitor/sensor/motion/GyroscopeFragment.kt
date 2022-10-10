package jp.nagano.northfield.sensormonitor.sensor.motion

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class GyroscopeFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_gyroscope
    override val informationLabel = R.id.gyroscope_info
    override val statusLabel = R.id.gyroscope_status
    override val sensor = GyroscopeSensor()
    override val logTag = "GyroscopeFragment"
    override val tabName = "Gyroscope"
}