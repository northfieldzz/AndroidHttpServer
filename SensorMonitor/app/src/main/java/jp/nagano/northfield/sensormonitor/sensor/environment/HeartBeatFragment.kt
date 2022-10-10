package jp.nagano.northfield.sensormonitor.sensor.environment

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class HeartBeatFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_heart_beat
    override val informationLabel = R.id.heart_beat_info
    override val statusLabel = R.id.heart_beat_status
    override val sensor = HeartBeatSensor()
    override val logTag = "HeartBeatFragment"
    override val tabName = "Heart Beat"
}