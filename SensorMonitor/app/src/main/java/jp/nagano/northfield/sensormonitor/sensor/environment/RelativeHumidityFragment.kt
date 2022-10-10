package jp.nagano.northfield.sensormonitor.sensor.environment

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class RelativeHumidityFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_relative_humidity
    override val informationLabel = R.id.relative_humidity_info
    override val statusLabel = R.id.relative_humidity_status
    override val sensor = RelativeHumiditySensor()
    override val logTag = "RelativeHumidityFragment"
    override val tabName = "Relative Humidity"
}