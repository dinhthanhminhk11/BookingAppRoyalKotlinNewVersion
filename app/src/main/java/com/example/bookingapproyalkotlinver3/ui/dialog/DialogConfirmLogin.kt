package com.example.bookingapproyalkotlinver3.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import com.example.bookingapproyalkotlinver3.databinding.LayoutConfirmLoginBinding

class DialogConfirmLogin(context: Context, private val onLogoutClick: () -> Unit) :
    Dialog(context) {
    private lateinit var binding: LayoutConfirmLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = LayoutConfirmLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            onLogoutClick.invoke()
            dismiss()
        }
    }

    companion object {
        fun create(context: Context, onLogoutClick: () -> Unit): DialogConfirmLogin {
            return DialogConfirmLogin(context, onLogoutClick)
        }
    }
}