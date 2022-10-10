package jp.nagano.northfield.sensormonitor.sensor.motion

import android.os.Build
import androidx.annotation.RequiresApi
import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class AccelerometerUncalibratedFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_accelerometer_uncalibrated
    override val informationLabel = R.id.accelerometer_uncalibrated_info
    override val statusLabel = R.id.accelerometer_uncalibrated_status

    @RequiresApi(Build.VERSION_CODES.O)
    override val sensor = AccelerometerUncalibratedSensor()
    override val logTag = "AccelerometerUncalibratedFragment"
    override val tabName = "Accelerometer Uncalibrated"
}