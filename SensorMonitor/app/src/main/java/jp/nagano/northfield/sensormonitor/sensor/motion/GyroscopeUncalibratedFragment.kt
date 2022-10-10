package jp.nagano.northfield.sensormonitor.sensor.motion

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class GyroscopeUncalibratedFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_gyroscope_uncalibrated
    override val informationLabel = R.id.gyroscope_uncalibrated_info
    override val statusLabel = R.id.gyroscope_uncalibrated_status
    override val sensor = GyroscopeUncalibratedSensor()
    override val logTag = "GyroscopeUncalibratedFragment"
    override val tabName = "Gyroscope Uncalibrated"
}