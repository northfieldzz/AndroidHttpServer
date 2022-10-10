package jp.nagano.northfield.sensormonitor.sensor.environment

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class HeartRateFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_heart_rate
    override val informationLabel = R.id.heart_rate_info
    override val statusLabel = R.id.heart_rate_status
    override val sensor = HeartRateSensor()
    override val logTag = "HeartRateFragment"
    override val tabName = "Heart Rate"
}