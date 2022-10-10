package jp.nagano.northfield.sensormonitor.sensor.position

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class ProximityFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_proximity
    override val informationLabel = R.id.proximity_info
    override val statusLabel = R.id.proximity_status
    override val sensor = ProximitySensor()
    override val logTag = "ProximityFragment"
    override val tabName = "Proximity"
}