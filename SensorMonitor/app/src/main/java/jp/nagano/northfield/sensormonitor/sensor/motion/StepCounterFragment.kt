package jp.nagano.northfield.sensormonitor.sensor.motion

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class StepCounterFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_step_counter
    override val informationLabel = R.id.step_counter_info
    override val statusLabel = R.id.step_counter_status
    override val sensor = StepCounterSensor()
    override val logTag = "StepCounterFragment"
    override val tabName = "Step Counter"
}