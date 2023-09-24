package com.example.bookingapproyalkotlinver3.ui.fragment.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.constant.MySharedPreferences
import com.example.bookingapproyalkotlinver3.constant.isValidEmail
import com.example.bookingapproyalkotlinver3.constant.setUnderlinedText
import com.example.bookingapproyalkotlinver3.data.model.user.UserClient
import com.example.bookingapproyalkotlinver3.data.model.user.UserLogin
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.databinding.FragmentLoginBinding
import com.example.bookingapproyalkotlinver3.ui.customview.toast.CookieBar
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseViewModelFragment<FragmentLoginBinding>() {
    private val viewModel: MainViewModel by viewModels()

    override fun initView() {
        binding.tvSignUp.setUnderlinedText(binding.tvSignUp.text.toString())
        binding.tvForgotPass.setUnderlinedText(binding.tvForgotPass.text.toString())
    }

    override fun initOnClickListener() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnSignIn.setOnClickListener {
            if (binding.edEmail.text.isEmpty()) {
                CookieBar.build(requireActivity())
                    .setTitle(getString(R.string.Notify))
                    .setMessage(getString(R.string.enterMail))
                    .setIcon(R.drawable.ic_warning_icon_check)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show()
            } else if (!isValidEmail(binding.edEmail.text.toString())) {
                CookieBar.build(requireActivity())
                    .setTitle(getString(R.string.Notify))
                    .setMessage(getString(R.string.enterMailFaild))
                    .setIcon(R.drawable.ic_warning_icon_check)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show()
            } else if (binding.edPass.text.isEmpty()) {
                CookieBar.build(requireActivity())
                    .setTitle(getString(R.string.Notify))
                    .setMessage(getString(R.string.enterPass))
                    .setIcon(R.drawable.ic_warning_icon_check)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show()
            } else {
                isLoaded(true)
                viewModel.login(
                    UserLogin(
                        binding.edEmail.text.toString(),
                        binding.edPass.text.toString()
                    )
                )
            }
        }
    }

    override fun observeLiveData() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    isLoaded(false)
                    it.data?.let { it ->
                        if(it.status){
                            MySharedPreferences.getInstance(requireActivity())
                                .putString(AppConstant.TOKEN_USER, it.token)
                            if (it.user.active) {
                                if (it.user.checkAccount) {
                                    UserClient.email = it.user.email
                                    UserClient.id = it.user.id
                                    UserClient.name = it.user.name
                                    UserClient.image = it.user.image
                                    UserClient.phone = it.user.phone
                                    UserClient.address = it.user.address
                                    UserClient.countBooking = it.user.countBooking
                                    findNavController().popBackStack()
                                } else {
                                    // trường hợp account bị ban
                                }
                            } else {
                                // trường hợp email chưa xác thực
                            }
                        }else{
                            CookieBar.build(requireActivity())
                                .setTitle(getString(R.string.Notify))
                                .setMessage(it.message)
                                .setIcon(R.drawable.ic_warning_icon_check)
                                .setTitleColor(R.color.black)
                                .setMessageColor(R.color.black)
                                .setDuration(3000)
                                .setBackgroundRes(R.drawable.background_toast)
                                .setCookiePosition(CookieBar.BOTTOM)
                                .show()
                        }
                    }
                }

                is Resource.Error -> {
                    isLoaded(false)
                }

                is Resource.Loading -> {
                    isLoaded(true)
                }
            }
        }
    }

    override fun initData() {
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)


    private fun isLoaded(checkLoad: Boolean) {
        if (checkLoad) {
            binding.btnSignIn.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnSignIn.isEnabled = true
            binding.progressBar.visibility = View.GONE
        }
    }
}