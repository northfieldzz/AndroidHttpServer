package jp.nagano.northfield.sensormonitor.sensor.detector

import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.widget.Button
import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class StationaryDetectFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_stationary_detect
    override val informationLabel = R.id.stationary_detect_info
    override val statusLabel = R.id.stationary_detect_status
    override val sensor = StationaryDetectSensor()
    override val logTag = "StationaryDetectFragment"
    override val tabName = "Stationary Detect"

    override fun setEventListener() {
        sensorTriggerEventListener = object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent) {
                status?.text = sensor.getString(event.values)
            }
        }
        val resetButton = requireView().findViewById(R.id.stationary_detect_reset_button) as Button
        resetButton.setOnClickListener { registerListener() }
    }
}