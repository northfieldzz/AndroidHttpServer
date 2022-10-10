package jp.nagano.northfield.sensormonitor.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.TriggerEventListener
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import jp.nagano.northfield.sensormonitor.ui.TabItemFragment
import jp.nagano.northfield.sensormonitor.R

abstract class SensorFragmentBase : TabItemFragment() {
    abstract val inflaterLabel: Int
    abstract val informationLabel: Int
    abstract val statusLabel: Int
    abstract val sensor: SensorInterface
    open val logTag: String = "SensorFragmentBase"

    var sensorManager: SensorManager? = null
    var information: TextView? = null
    var status: TextView? = null
    var sensorEventListener: SensorEventListener? = null
    var sensorTriggerEventListener: TriggerEventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(inflaterLabel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        information = view.findViewById(informationLabel) as TextView
        status = view.findViewById(statusLabel) as TextView
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        setEventListener()
//        viewModel = ViewModelProvider(this).get(GyroscopeViewModel::class.java)
    }

    open fun setEventListener() {
        sensorEventListener = AppSensorEventListener(status!!, sensor)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        registerListener()
    }

    fun registerListener() {
        val sensorModule = sensorManager!!.getDefaultSensor(sensor.type)
        sensorModule?.also { sensor ->
            sensorEventListener?.let { listener ->
                sensorManager?.registerListener(
                    listener,
                    sensor,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }
            sensorTriggerEventListener?.let {
                sensorManager?.requestTriggerSensor(it, sensor)
                status?.text = getString(R.string.no_signal)
            }
            information?.text = infoText(sensor)
        } ?: run {
            information?.text = getString(R.string.unavailable)
            status?.text = getString(R.string.no_answer)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(sensorEventListener)
    }

    private fun infoText(sensor: Sensor): String {
        var stinfo = "unknown"
        when (sensor.reportingMode) {
            0 -> stinfo = "REPORTING_MODE_CONTINUOUS"
            1 -> stinfo = "REPORTING_MODE_ON_CHANGE"
            2 -> stinfo = "REPORTING_MODE_ONE_SHOT"
        }
        return """
            Name         : ${sensor.name}
            Vendor       : ${sensor.vendor}
            Type         : ${sensor.type}
            MinDelay     : ${sensor.minDelay}usec
            MaxDelay     : ${sensor.maxDelay}usec
            ReportingMode: $stinfo
            MaxRange     : ${sensor.maximumRange}
            Resolution   : ${sensor.resolution}m/s
            Power        : ${sensor.power}mA"
            """.trimIndent()
    }
}