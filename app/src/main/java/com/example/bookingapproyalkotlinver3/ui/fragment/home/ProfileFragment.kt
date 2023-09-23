package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.databinding.FragmentProfileBinding

class ProfileFragment : BaseViewModelFragment<FragmentProfileBinding>() {
    override fun initView() {
    }

    override fun initOnClickListener() {
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_kingMainFragment_to_loginFragment)
        }
    }

    override fun observeLiveData() {
    }

    override fun initData() {
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
}