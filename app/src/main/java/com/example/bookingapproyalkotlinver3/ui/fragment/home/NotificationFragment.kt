package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.databinding.FragmentChatBinding
import com.example.bookingapproyalkotlinver3.databinding.FragmentHomeBinding
import com.example.bookingapproyalkotlinver3.databinding.FragmentNotificationBinding

class NotificationFragment : BaseViewModelFragment<FragmentNotificationBinding>() {
    override fun initView() {
    }

    override fun initOnClickListener() {
    }

    override fun observeLiveData() {
    }

    override fun initData() {
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false)
}