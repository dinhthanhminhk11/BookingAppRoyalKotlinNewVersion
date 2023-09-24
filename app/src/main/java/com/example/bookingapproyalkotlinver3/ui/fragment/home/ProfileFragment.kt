package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.icu.text.CaseMap.Title
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.setUnderlinedText
import com.example.bookingapproyalkotlinver3.data.model.SettingItem
import com.example.bookingapproyalkotlinver3.databinding.FragmentProfileBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemSettingUserFragmentBinding
import com.example.bookingapproyalkotlinver3.databinding.LayoutCheckLoginSettingBinding
import com.example.bookingapproyalkotlinver3.databinding.TitleTextViewBinding

class ProfileFragment : BaseViewModelFragment<FragmentProfileBinding>() {
    private lateinit var adapterSetting: SettingAdapter
    val listSetting = mutableListOf<SettingItem>()
    override fun initView() {
        listSetting.add(
            SettingItem(
                viewType = 3,
                background = 30,
                iconResourceLeft = 0,
                title = getString(R.string.textHoso),
                description = ""
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 2,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.baseline_person_outline_24,
                title = getString(R.string.info_myself),
                description = getString(R.string.info_myself_edit)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 3,
                background = 20,
                iconResourceLeft = 0,
                title = getString(R.string.setting_arrays),
                description = ""
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 1,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.baseline_person_outline_24,
                title = getString(R.string.info_myself),
                description = getString(R.string.info_myself_edit)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 1,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.icon_bell_main,
                title = getString(R.string.notification_arrays),
                description = getString(R.string.notification_arrays_text)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 1,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.ic_payment_svgrepo_com,
                title = getString(R.string.yourpayment),
                description = getString(R.string.textInfoTextPayment)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 1,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.ic_icon_change_pass_setting,
                title = getString(R.string.ChangePassSetting),
                description = getString(R.string.LanguageSetting2)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 1,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.ic_internet_language_settings_ver3,
                title = getString(R.string.LanguageSetting),
                description = getString(R.string.LanguageSetting_text)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 1,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.ic_group_moon_setting,
                title = getString(R.string.theme),
                description = getString(R.string.Light)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 3,
                background = 20,
                iconResourceLeft = 0,
                title = getString(R.string.help_arrays),
                description = ""
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 1,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.ic_icon_logo_app,
                title = getString(R.string.byRoyal),
                description = getString(R.string.infoRoyal)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 1,
                background = R.drawable.background_setting_item,
                iconResourceLeft = R.drawable.ic_helps_white_menu,
                title = getString(R.string.question_bookmard2),
                description = getString(R.string.question_help1)
            )
        )

        listSetting.add(
            SettingItem(
                viewType = 3,
                background = 20,
                iconResourceLeft = 0,
                title = getString(R.string.logout_arrays),
                description = "",
                isVisible = false
            )
        )

        adapterSetting = SettingAdapter(listSetting)
        binding.listSetting.layoutManager = LinearLayoutManager(requireActivity())
        binding.listSetting.adapter = adapterSetting

    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {
    }

    override fun initData() {
    }

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    inner class SettingAdapter(private val itemList: List<SettingItem>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val DEFAULT_TYPE = 1
        private val CHECK_LOGIN_TYPE = 2
        private val TEXT_VIEW_TYPE = 3

        inner class ViewHolderDefault(val binding: ItemSettingUserFragmentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: SettingItem) {
                if (item.isVisible) {
                    binding.contentBackground.setBackgroundResource(item.background)
                    binding.title.text = item.title
                    binding.content.text = item.description
                    binding.iconLeft.setImageResource(item.iconResourceLeft)
                    binding.iconRight.setImageResource(item.iconResourceRight)
                } else {
                    itemView.visibility = View.GONE
                }

            }
        }

        inner class ViewHolderTitle(val binding: TitleTextViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: SettingItem) {
                binding.titleText.text = item.title
                binding.titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.background.toFloat())
                if(!item.isVisible){
                    binding.titleText.setUnderlinedText(binding.titleText.text.toString())
                }
            }
        }

        inner class ViewHolderCheckLogin(val binding: LayoutCheckLoginSettingBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: SettingItem) {
                binding.tvSignUpProfile.setUnderlinedText(binding.tvSignUpProfile.text.toString())
                binding.login.setOnClickListener {
                    findNavController().navigate(R.id.action_kingMainFragment_to_loginFragment)
                    listSetting.clear()
                }
                binding.tvSignUpProfile.setOnClickListener {
                    findNavController().navigate(R.id.action_kingMainFragment_to_registerFragment)
                    listSetting.clear()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                DEFAULT_TYPE -> {
                    val view = ItemSettingUserFragmentBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                    ViewHolderDefault(view)
                }

                CHECK_LOGIN_TYPE -> {
                    val view = LayoutCheckLoginSettingBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                    ViewHolderCheckLogin(view)
                }

                TEXT_VIEW_TYPE -> {
                    val view = TitleTextViewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                    ViewHolderTitle(view)
                }

                else -> throw IllegalArgumentException("Unknown view type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = listSetting[position]
            when (holder.itemViewType) {
                DEFAULT_TYPE -> {
                    val viewHolderDefault = holder as ViewHolderDefault
                    viewHolderDefault.bind(item)
                }

                CHECK_LOGIN_TYPE -> {
                    val viewHolderCheckLogin = holder as ViewHolderCheckLogin
                    viewHolderCheckLogin.bind(item)
                }

                TEXT_VIEW_TYPE -> {
                    val viewHolderTitle = holder as ViewHolderTitle
                    viewHolderTitle.bind(item)
                }
            }
        }

        override fun getItemCount(): Int = itemList.size

        override fun getItemViewType(position: Int): Int = listSetting[position].viewType

    }
}