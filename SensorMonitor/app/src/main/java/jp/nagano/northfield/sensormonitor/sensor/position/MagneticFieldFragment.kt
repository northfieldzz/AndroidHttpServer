package jp.nagano.northfield.sensormonitor.sensor.position

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class MagneticFieldFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_magnetic_field
    override val informationLabel = R.id.magnetic_field_info
    override val statusLabel = R.id.magnetic_field_status
    override val sensor = MagneticFieldSensor()
    override val logTag = "MagneticFieldFragment"
    override val tabName = "Magnetic Field"
}