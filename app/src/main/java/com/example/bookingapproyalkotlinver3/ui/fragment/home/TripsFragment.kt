package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.databinding.FragmentTripsBinding

class TripsFragment : BaseViewModelFragment<FragmentTripsBinding>() {
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
    ): FragmentTripsBinding = FragmentTripsBinding.inflate(inflater, container, false)
}