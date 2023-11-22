package com.example.bookingapproyalkotlinver3.ui.fragment.setting

import androidx.navigation.fragment.findNavController
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseFragment
import com.example.bookingapproyalkotlinver3.databinding.FragmentAboutUsBinding


class AboutUsFragment : BaseFragment<FragmentAboutUsBinding>(FragmentAboutUsBinding::inflate) {
    override fun initView() {
        binding.toolBar.title = getString(R.string.veRoyalJourney)
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {
    }

    override fun initData() {
    }

}