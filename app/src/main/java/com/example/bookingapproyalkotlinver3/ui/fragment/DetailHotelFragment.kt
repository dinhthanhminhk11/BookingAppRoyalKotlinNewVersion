package com.example.bookingapproyalkotlinver3.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.databinding.FragmentDetailHotelActivityBinding


class DetailHotelFragment : BaseViewModelFragment<FragmentDetailHotelActivityBinding>() {
    override fun initView() {
        onBackCustom()
    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {

    }

    override fun initData() {

    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDetailHotelActivityBinding = FragmentDetailHotelActivityBinding.inflate(inflater, container, false)

}