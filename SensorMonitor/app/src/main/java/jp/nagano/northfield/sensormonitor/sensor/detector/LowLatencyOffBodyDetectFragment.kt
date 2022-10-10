package jp.nagano.northfield.sensormonitor.sensor.detector

import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.widget.Button
import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class LowLatencyOffBodyDetectFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_low_latency_off_body_detect
    override val informationLabel = R.id.low_latency_off_body_detect_info
    override val statusLabel = R.id.low_latency_off_body_detect_status
    override val sensor = LowLatencyOffBodyDetectSensor()
    override val logTag = "LowLatencyOffBodyDetectFragment"
    override val tabName = "Low Latency OffBody Detect"

    override fun setEventListener() {
        sensorTriggerEventListener = object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent) {
                status?.text = sensor.getString(event.values)
            }
        }
        val resetButton =
            requireView().findViewById(R.id.low_latency_offbody_detect_reset_button) as Button
        resetButton.setOnClickListener { registerListener() }
    }
}