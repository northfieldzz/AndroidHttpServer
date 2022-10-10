package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.widget.Button
import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class StepDetectorFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_step_detector
    override val informationLabel = R.id.step_detector_info
    override val statusLabel = R.id.step_detector_status
    override val sensor = StepDetectorSensor()
    override val logTag = "StepDetectorFragment"
    override val tabName = "Step Detector"

    override fun setEventListener() {
        sensorTriggerEventListener = object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent) {
                status?.text = sensor.getString(event.values)
            }
        }
        val resetButton = requireView().findViewById(R.id.step_detector_reset_button) as Button
        resetButton.setOnClickListener { registerListener() }
    }
}