package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.constant.MySharedPreferences
import com.example.bookingapproyalkotlinver3.constant.loadImage
import com.example.bookingapproyalkotlinver3.data.model.bookmark.Bookmark
import com.example.bookingapproyalkotlinver3.data.model.notification.Notification
import com.example.bookingapproyalkotlinver3.data.model.user.UserClient
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.databinding.FragmentBookmarkBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemBookmarkByUserBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemLayoutNotificationBinding
import com.example.bookingapproyalkotlinver3.ui.fragment.setting.NotificationFragment
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookmarkFragment : BaseViewModelFragment<FragmentBookmarkBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var bookmarkAdapter: BookmarkAdapter
    override fun initView() {
    }

    override fun initOnClickListener() {
    }

    override fun observeLiveData() {
        viewModel.bookmarkResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {

                    }
                }

                is Resource.Error -> {
                    it.message?.let {

                    }
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    override fun initData() {
        if (!MySharedPreferences.getInstance(requireActivity())
                .getString(AppConstant.TOKEN_USER, "")
                .isNullOrEmpty()
        ) {
            // check đã đăng nhập
            viewModel.getListBookmarkByUser(UserClient.id.toString())
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentBookmarkBinding = FragmentBookmarkBinding.inflate(inflater, container, false)

    inner class BookmarkAdapter(
        private val onItemClickListener: (Bookmark) -> Unit
    ) : ListAdapter<Bookmark, BookmarkAdapter.ViewHolder>(BookmarkDiffCallback()) {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BookmarkAdapter.ViewHolder {
            val binding = ItemBookmarkByUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {
            val bookmark = getItem(position)
            holder.bind(bookmark)
        }

        inner class ViewHolder(val binding: ItemBookmarkByUserBinding) :
            RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(item: Bookmark) {

            }
        }
    }

    class BookmarkDiffCallback : DiffUtil.ItemCallback<Bookmark>() {
        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
            return oldItem == newItem
        }
    }
}