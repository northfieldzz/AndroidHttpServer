package jp.nagano.northfield.sensormonitor.sensor.motion

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class AccelerometerFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_accelerometer
    override val informationLabel = R.id.accelerometer_info
    override val statusLabel = R.id.accelerometer_status
    override val sensor = AccelerometerSensor()
    override val logTag = "AccelerometerFragment"
    override val tabName = "Accelerometer"
}

