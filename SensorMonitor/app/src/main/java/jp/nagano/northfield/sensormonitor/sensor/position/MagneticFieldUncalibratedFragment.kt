package jp.nagano.northfield.sensormonitor.sensor.position

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class MagneticFieldUncalibratedFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_magnetic_field_uncalibrated
    override val informationLabel = R.id.magnetic_field_uncalibrated_info
    override val statusLabel = R.id.magnetic_field_uncalibrated_status
    override val sensor = MagneticFieldUncalibratedSensor()
    override val logTag = "MagneticFieldUncalibratedFragment"
    override val tabName = "Magnetic Field Uncalibrated"
}