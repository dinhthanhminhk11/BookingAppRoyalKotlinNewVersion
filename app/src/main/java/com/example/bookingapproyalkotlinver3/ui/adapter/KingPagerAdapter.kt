package com.example.bookingapproyalkotlinver3.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookingapproyalkotlinver3.ui.fragment.home.HomeFragment

class KingPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
//            1 -> NotificationFragment()
//            2 -> ChatFragment()
//            3 -> ChatFragment()
//            4 -> UserFragment()
            else -> HomeFragment()
        }
    }
}