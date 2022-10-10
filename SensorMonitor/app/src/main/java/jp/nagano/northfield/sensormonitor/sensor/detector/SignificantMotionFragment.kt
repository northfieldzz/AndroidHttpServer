package jp.nagano.northfield.sensormonitor.sensor.detector

import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.widget.Button
import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class SignificantMotionFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_significant_motion
    override val informationLabel = R.id.significant_motion_info
    override val statusLabel = R.id.significant_motion_status
    override val sensor = SignificantMotionSensor()
    override val logTag = "SignificantMotionFragment"
    override val tabName = "Significant Motion"

    override fun setEventListener() {
        sensorTriggerEventListener = object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent) {
                status?.text = sensor.getString(event.values)
            }
        }
        val resetButton = requireView().findViewById(R.id.significant_motion_reset_button) as Button
        resetButton.setOnClickListener { registerListener() }
    }
}

