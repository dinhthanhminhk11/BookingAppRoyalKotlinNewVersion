package com.example.bookingapproyalkotlinver3.ui.fragment.setting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.loadImage
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

        notificationAdapter = NotificationAdapter {
            if (it.isSeem) {
                viewModel.updateNotification(it.id)
            } else {
                // Xử lý khi notification chưa đọc
            }
        }

        binding.listNotification.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = notificationAdapter
        }
    }

    private fun initToolbar() {
        binding.toolBar.title = getString(R.string.notification_arrays)
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initOnClickListener() {
        binding.reLoad.setOnRefreshListener {
            viewModel.getListNotification(UserClient.id.toString())
            binding.reLoad.isRefreshing = false
        }
    }

    override fun observeLiveData() {
        viewModel.notificationResponse.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { notifications ->
                        val notificationList = notifications.data
                        binding.contentNullList.visibility =
                            if (notificationList.isNotEmpty()) View.VISIBLE else View.GONE
                        notificationAdapter.submitList(notificationList)
                    }
                }

                is Resource.Error -> {
                    resource.message?.let {}
                }

                is Resource.Loading -> {
                }

                else -> {}
            }
        }
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListNotification(UserClient.id.toString())
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false)

    inner class NotificationAdapter(
        private val onItemClickListener: (Notification) -> Unit
    ) : ListAdapter<Notification, NotificationAdapter.ViewHolder>(NotificationDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemLayoutNotificationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val notification = getItem(position)
            holder.bind(notification)
        }

        inner class ViewHolder(val binding: ItemLayoutNotificationBinding) :
            RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(item: Notification) {
                loadImage(itemView.context, item.imageHoust, binding.image)

                binding.content.text = item.content
                binding.dateAndTime.text = item.date + " " + item.time
                binding.title.text = item.title

                if (item.isSeem) {
                    itemView.setBackgroundResource(R.drawable.background_not_seem)
                } else {
                    itemView.setBackgroundResource(R.drawable.background_seem)
                }

                binding.root.setOnClickListener {
                    onItemClickListener.invoke(item)
                }
            }
        }
    }

    class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }
}