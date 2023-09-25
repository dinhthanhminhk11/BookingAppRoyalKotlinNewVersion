package com.example.bookingapproyalkotlinver3.ui.fragment.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.databinding.FragmentAboutUsBinding


class AboutUsFragment : BaseViewModelFragment<FragmentAboutUsBinding>() {
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

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutUsBinding = FragmentAboutUsBinding.inflate(inflater, container, false)

}