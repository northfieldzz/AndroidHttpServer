package jp.nagano.northfield.sensormonitor.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.nagano.northfield.sensormonitor.ui.TabItemFragment
import jp.nagano.northfield.sensormonitor.R


class HomeFragment : TabItemFragment() {
    override val tabName: String = "Home"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}