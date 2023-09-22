package com.example.bookingapproyalkotlinver3.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.databinding.FragmentKingMainBinding
import com.example.bookingapproyalkotlinver3.ui.adapter.KingPagerAdapter


class KingMainFragment : BaseViewModelFragment<FragmentKingMainBinding>()  {
    private var backPressedTime: Long = 0
    private val BACK_PRESS_INTERVAL: Long = 2000
    override fun initView() {
        duplicationBack()

        val viewPager: ViewPager2 = binding.viewPager
        val pagerAdapter = KingPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

        setStatusBarStyle(AppConstant.TYPE_LIGHT, Color.WHITE)

        viewPager.isUserInputEnabled =
            false

        binding.navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_discover -> viewPager.setCurrentItem(0, false)
                R.id.navigation_favorites -> viewPager.setCurrentItem(1, false)
                R.id.navigation_trips -> viewPager.setCurrentItem(2, false)
                R.id.navigation_inbox -> viewPager.setCurrentItem(3, false)
                R.id.navigation_profile -> viewPager.setCurrentItem(4, false)
            }
            true
        }
    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {

    }

    override fun initData() {

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentKingMainBinding {
        return FragmentKingMainBinding.inflate(inflater, container, false)
    }

    private fun duplicationBack() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                if (currentTime - backPressedTime > BACK_PRESS_INTERVAL) {
                    Toast.makeText(
                        requireContext(), getString(R.string.dup_back), Toast.LENGTH_SHORT
                    ).show()
                    backPressedTime = currentTime
                } else {
                    isEnabled = false
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, onBackPressedCallback
        )
    }
}