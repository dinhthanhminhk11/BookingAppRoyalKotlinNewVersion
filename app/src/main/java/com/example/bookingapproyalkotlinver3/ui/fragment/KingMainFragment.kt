package com.example.bookingapproyalkotlinver3.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.data.util.view.setStatusBarStyle
import com.example.bookingapproyalkotlinver3.data.util.view.showToastError
import com.example.bookingapproyalkotlinver3.databinding.FragmentKingMainBinding
import com.example.bookingapproyalkotlinver3.ui.adapter.KingPagerAdapter
import com.example.bookingapproyalkotlinver3.ui.customview.toast.CookieBar


class KingMainFragment : BaseFragment<FragmentKingMainBinding>(FragmentKingMainBinding::inflate) {
    private var backPressedTime: Long = 0
    private val BACK_PRESS_INTERVAL: Long = 2000

    @SuppressLint("UseRequireInsteadOfGet")
    override fun initView() {
        duplicationBack()

        val viewPager: ViewPager2 = binding.viewPager
        val pagerAdapter = KingPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

        setStatusBarStyle(AppConstant.TYPE_LIGHT, Color.WHITE, activity!!)

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

    @SuppressLint("UseRequireInsteadOfGet")
    private fun duplicationBack() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                if (currentTime - backPressedTime > BACK_PRESS_INTERVAL) {
                    showToastError(activity!! , getString(R.string.Notify) , getString(R.string.dup_back))
                    backPressedTime = currentTime
                } else {
                    isEnabled = false
                    activity!!.finish()
                }
            }
        }
        activity!!.onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, onBackPressedCallback
        )
    }
}