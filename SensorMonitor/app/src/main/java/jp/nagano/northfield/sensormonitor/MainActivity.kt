package jp.nagano.northfield.sensormonitor

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import jp.nagano.northfield.sensormonitor.home.HomeFragment
import jp.nagano.northfield.sensormonitor.sensor.detector.LowLatencyOffBodyDetectFragment
import jp.nagano.northfield.sensormonitor.sensor.detector.SignificantMotionFragment
import jp.nagano.northfield.sensormonitor.sensor.detector.StationaryDetectFragment
import jp.nagano.northfield.sensormonitor.sensor.environment.*
import jp.nagano.northfield.sensormonitor.sensor.motion.*
import jp.nagano.northfield.sensormonitor.sensor.position.*
import jp.nagano.northfield.sensormonitor.ui.TabItemFragment


class MainActivity : AppCompatActivity() {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val tabs = arrayOf<TabItemFragment>(
            HomeFragment(),
            AccelerometerFragment(),
            AccelerometerUncalibratedFragment(),
            GravityFragment(),
            GyroscopeFragment(),
            GyroscopeUncalibratedFragment(),
            LinearAccelerationFragment(),
            RotationVectorFragment(),
            SignificantMotionFragment(),
            StationaryDetectFragment(),
            StepCounterFragment(),
            StepDetectorFragment(),
            GameRotationVectorFragment(),
            GeomagneticRotationVectorFragment(),
            Pose6DOFFragment(),
            MagneticFieldFragment(),
            MagneticFieldUncalibratedFragment(),
            ProximityFragment(),
            LowLatencyOffBodyDetectFragment(),
            LightFragment(),
            AmbientTemperatureFragment(),
            PressureFragment(),
            RelativeHumidityFragment(),
            HeartBeatFragment(),
            HeartRateFragment(),
            MotionDetectFragment(),
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val viewPager = findViewById<ViewPager2>(R.id.pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, tabs)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position].tabName
        }.attach()
    }
}
