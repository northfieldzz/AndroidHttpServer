package jp.nagano.northfield.sensormonitor.sensor.position

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class GeomagneticRotationVectorFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_geomagnetic_rotation_vector
    override val informationLabel = R.id.geomagnetic_rotation_vector_info
    override val statusLabel = R.id.geomagnetic_rotation_vector_status
    override val sensor = GeomagneticRotationVectorSensor()
    override val logTag = "GeomagneticRotationVectorFragment"
    override val tabName = "Geomagnetic Rotation Vector"
}