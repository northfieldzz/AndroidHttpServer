package jp.nagano.northfield.sensormonitor.sensor.motion

import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.widget.Button
import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class MotionDetectFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_motion_detect
    override val informationLabel = R.id.motion_detect_info
    override val statusLabel = R.id.motion_detect_status
    override val sensor = MotionDetectSensor()
    override val logTag = "MotionDetectFragment"
    override val tabName = "Motion Detect"

    override fun setEventListener() {
        sensorTriggerEventListener = object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent) {
                status?.text = sensor.getString(event.values)
            }
        }
        val resetButton = requireView().findViewById(R.id.motion_detect_reset_button) as Button
        resetButton.setOnClickListener { registerListener() }
    }
}