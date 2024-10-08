package com.example.goldsecure

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.goldsecure.ui.dashboard.DashboardFragment
import com.example.goldsecure.ui.home.HomeFragment
import com.example.goldsecure.ui.notifications.NotificationsFragment

class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> DashboardFragment()
            2 -> NotificationsFragment()
            else -> HomeFragment()
        }
    }
}
