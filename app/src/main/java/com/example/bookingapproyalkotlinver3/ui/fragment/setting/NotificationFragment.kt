package com.example.bookingapproyalkotlinver3.ui.fragment.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.loadImage
import com.example.bookingapproyalkotlinver3.data.model.hotel.Hotel
import com.example.bookingapproyalkotlinver3.data.model.notification.Notification
import com.example.bookingapproyalkotlinver3.data.model.user.UserClient
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.databinding.FragmentNotificationBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemLayoutNotificationBinding
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseViewModelFragment<FragmentNotificationBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var notificationAdapter: NotificationAdapter
    override fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        binding.toolBar.title = getString(R.string.notification_arrays)
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initOnClickListener() {
//        notificationAdapter.setOnItemClickListener {
//            if (it.isSeem) {
//                // update trangj thai xem
//            } else {
//
//            }
//        }
    }

    override fun observeLiveData() {
        viewModel.notificationResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        notificationAdapter = NotificationAdapter(it.data)
                        binding.listNotification.apply {
                            adapter = notificationAdapter
                            layoutManager = LinearLayoutManager(requireActivity())
                        }
                    }
                }

                is Resource.Error -> {
                    it.message?.let {

                    }
                }

                is Resource.Loading -> {

                }

                else -> {}
            }
        }
    }

    override fun initData() {
        viewModel.getListNotification(UserClient.id.toString())
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false)

    inner class NotificationAdapter(private val data: List<Notification>?) :
        RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
        private var backgroundNotSeem: Int = R.drawable.background_not_seem
        private var backgroundSeem: Int = R.drawable.background_seem
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemLayoutNotificationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int = data?.size ?: 0

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val notification = data?.get(position)
            notification?.let { holder.bind(it) }
        }


        inner class ViewHolder(val binding: ItemLayoutNotificationBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Notification) {
                loadImage(itemView.context, item.imageHoust, binding.image)

                binding.content.text = item.content
                binding.dateAndTime.text = item.date + " " + item.time
                binding.title.text = item.title

                if (item.isSeem) {
                    itemView.setBackgroundResource(backgroundNotSeem)
                } else {
                    itemView.setBackgroundResource(backgroundSeem)
                }

                binding.root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }

        private var onItemClickListener: ((Notification) -> Unit)? = null
        fun setOnItemClickListener(listener: (Notification) -> Unit) {
            onItemClickListener = listener
        }
    }
}