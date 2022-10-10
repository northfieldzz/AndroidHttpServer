package jp.nagano.northfield.sensormonitor.sensor.motion

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class RotationVectorFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_rotation_vector
    override val informationLabel = R.id.rotation_vector_info
    override val statusLabel = R.id.rotation_vector_status
    override val sensor = RotationVectorSensor()
    override val logTag = "RotationVectorFragment"
    override val tabName = "Rotation Vector"
}