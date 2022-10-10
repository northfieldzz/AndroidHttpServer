package jp.nagano.northfield.sensormonitor.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.widget.TextView

class AppSensorEventListener(
    private val status: TextView,
    private val sensor: SensorInterface
) : SensorEventListener {

    override fun onSensorChanged(p0: SensorEvent) {
        if (p0.sensor.type == sensor.type) {
            status.text = sensor.getString(p0.values)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
}
