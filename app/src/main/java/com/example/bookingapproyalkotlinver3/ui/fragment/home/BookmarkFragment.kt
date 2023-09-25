package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.databinding.FragmentBookmarkBinding
import com.example.bookingapproyalkotlinver3.databinding.FragmentNotificationBinding


class BookmarkFragment : BaseViewModelFragment<FragmentBookmarkBinding>() {
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
    ): FragmentBookmarkBinding = FragmentBookmarkBinding.inflate(inflater, container, false)

}