package com.example.bookingapproyalkotlinver3.ui.fragment.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.setUnderlinedText
import com.example.bookingapproyalkotlinver3.databinding.FragmentRegisterBinding


class RegisterFragment : BaseViewModelFragment<FragmentRegisterBinding>() {
    override fun initView() {
        binding.tvSignIn.setUnderlinedText(binding.tvSignIn.text.toString())

    }

    override fun initOnClickListener() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun observeLiveData() {
    }

    override fun initData() {
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

}