package jp.nagano.northfield.sensormonitor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import jp.nagano.northfield.sensormonitor.ui.TabItemFragment


class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments: Array<TabItemFragment>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = fragments.count()

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}