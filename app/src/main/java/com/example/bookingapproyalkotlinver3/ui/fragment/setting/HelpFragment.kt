package com.example.bookingapproyalkotlinver3.ui.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.databinding.FragmentAboutUsBinding
import com.example.bookingapproyalkotlinver3.databinding.FragmentHelpBinding


class HelpFragment : BaseViewModelFragment<FragmentHelpBinding>() {
    override fun initView() {
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
    ): FragmentHelpBinding = FragmentHelpBinding.inflate(inflater , container , false)

}