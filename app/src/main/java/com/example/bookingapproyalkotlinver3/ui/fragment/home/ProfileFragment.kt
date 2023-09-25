package com.example.bookingapproyalkotlinver3.ui.fragment.home

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseViewModelFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.constant.MySharedPreferences
import com.example.bookingapproyalkotlinver3.constant.loadImage
import com.example.bookingapproyalkotlinver3.constant.setUnderlinedText
import com.example.bookingapproyalkotlinver3.data.model.SettingItem
import com.example.bookingapproyalkotlinver3.data.model.user.UserClient
import com.example.bookingapproyalkotlinver3.databinding.FragmentProfileBinding
import com.example.bookingapproyalkotlinver3.databinding.ItemSettingUserFragmentBinding
import com.example.bookingapproyalkotlinver3.databinding.LayoutCheckLoginSettingBinding
import com.example.bookingapproyalkotlinver3.databinding.TitleTextViewBinding
import com.example.bookingapproyalkotlinver3.ui.customview.toast.CookieBar
import com.example.bookingapproyalkotlinver3.ui.dialog.DialogConfirmCustom

class ProfileFragment : BaseViewModelFragment<FragmentProfileBinding>() {
    private lateinit var adapterSetting: SettingAdapter
    val listSetting = mutableListOf<SettingItem>()

    override fun initView() {
        val token =
            MySharedPreferences.getInstance(requireActivity()).getString(AppConstant.TOKEN_USER, "")

        createListSetting(token)

        adapterSetting = SettingAdapter()
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

    inner class SettingAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                    binding.contentBackground.setBackgroundResource(item.background)
                    binding.title.text = item.title
                    binding.content.text = item.description
                    loadImage(binding.imgUser.context, UserClient.image.toString(), binding.imgUser)

                    binding.iconLeft.visibility = View.GONE
                    binding.imgUser.visibility = View.VISIBLE

                    binding.iconRight.setImageResource(item.iconResourceRight)
                    binding.iconRight.setColorFilter(resources.getColor(R.color.white))
                    binding.title.setTextColor(resources.getColor(R.color.white))
                    binding.content.setTextColor(resources.getColor(R.color.white))
                }

                binding.contentBackground.setOnClickListener {
                    item.tag?.let { tag ->
                        when (tag) {
                            AppConstant.TAG_SETTING_ABOUT_US -> navigateFragment(R.id.action_kingMainFragment_to_aboutUsFragment)
                            AppConstant.TAG_SETTING_CHANG_HELP -> navigateFragment(R.id.action_kingMainFragment_to_helpFragment)
                            AppConstant.TAG_SETTING_NOTIFICATION -> navigateFragment(R.id.action_kingMainFragment_to_notificationFragment)
                            else -> {}
                        }
                    }
                }

            }
        }

        inner class ViewHolderTitle(val binding: TitleTextViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("NotifyDataSetChanged")
            fun bind(item: SettingItem) {
                binding.titleText.text = item.title
                binding.titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.background.toFloat())
                if (!item.isVisible) {
                    binding.titleText.setUnderlinedText(binding.titleText.text.toString())
                    binding.titleText.setOnClickListener {
                        if (item.tag != null) {
                            if (item.tag == AppConstant.TAG_SETTING_LOGOUT) {
                                DialogConfirmCustom.create(
                                    requireActivity(), getString(R.string.textLogout2)
                                ) {
                                    MySharedPreferences.getInstance(requireActivity())
                                        .putString(AppConstant.TOKEN_USER, "")
                                    listSetting.clear()
                                    createListSetting(
                                        MySharedPreferences.getInstance(requireActivity())
                                            .getString(AppConstant.TOKEN_USER, "")
                                    )
                                    notifyDataSetChanged()
                                    CookieBar.build(requireActivity()).setTitle(R.string.Notify)
                                        .setMessage(R.string.logout_success)
                                        .setIcon(R.drawable.ic_complete_order)
                                        .setTitleColor(R.color.black).setMessageColor(R.color.black)
                                        .setDuration(3000)
                                        .setBackgroundRes(R.drawable.background_toast)
                                        .setCookiePosition(CookieBar.BOTTOM).show()
                                }.show()
                            }
                        }
                    }
                }
            }
        }

        inner class ViewHolderCheckLogin(val binding: LayoutCheckLoginSettingBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: SettingItem) {
                binding.tvSignUpProfile.setUnderlinedText(binding.tvSignUpProfile.text.toString())
                binding.login.setOnClickListener {
                    navigateFragment(R.id.action_kingMainFragment_to_loginFragment)
                }
                binding.tvSignUpProfile.setOnClickListener {
                    navigateFragment(R.id.action_kingMainFragment_to_registerFragment)
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

        override fun getItemCount(): Int = listSetting.size

        override fun getItemViewType(position: Int): Int = listSetting[position].viewType

    }

    private fun createSettingItem(
        tag: String? = null,
        viewType: Int,
        background: Int,
        iconResourceLeft: Int,
        iconResourceRight: Int = R.drawable.ic_button_infor_setting, // Thêm tham số này
        title: String,
        description: String,
        isVisible: Boolean = true
    ): SettingItem {
        return SettingItem(
            tag,
            viewType,
            background,
            iconResourceLeft,
            iconResourceRight,
            title,
            description,
            isVisible
        )
    }

    private fun createListSetting(token: String?) {
        listSetting.add(
            SettingItem(
                viewType = 3,
                background = 30,
                iconResourceLeft = 0,
                title = getString(R.string.textHoso),
                description = ""
            )
        )
        if (token.isNullOrEmpty()) {
            listSetting.addAll(
                listOf(
                    createSettingItem(
                        viewType = 2,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.baseline_person_outline_24,
                        title = getString(R.string.info_myself),
                        description = getString(R.string.info_myself_edit)
                    ), createSettingItem(
                        viewType = 3,
                        background = 20,
                        iconResourceLeft = 0,
                        title = getString(R.string.setting_arrays),
                        description = ""
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_CHANG_LANGUAGE,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_internet_language_settings_ver3,
                        title = getString(R.string.LanguageSetting),
                        description = getString(R.string.LanguageSetting_text)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_CHANG_THEME,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_group_moon_setting,
                        title = getString(R.string.theme),
                        description = getString(R.string.Light)
                    ), createSettingItem(
                        viewType = 3,
                        background = 20,
                        iconResourceLeft = 0,
                        title = getString(R.string.help_arrays),
                        description = ""
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_ABOUT_US,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_icon_logo_app,
                        title = getString(R.string.byRoyal),
                        description = getString(R.string.infoRoyal)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_CHANG_HELP,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_helps_white_menu,
                        title = getString(R.string.question_bookmard2),
                        description = getString(R.string.question_help1)
                    )
                )
            )
        } else {
            listSetting.addAll(
                listOf(
                    createSettingItem(
                        tag = AppConstant.TAG_SETTING_NOT_NULL_LOGIN,
                        viewType = 1,
                        background = R.drawable.background_setting_item_user,
                        iconResourceLeft = 0,
                        title = UserClient.name.toString(),
                        description = getString(R.string.show_profile_text),
                        isVisible = false
                    ), createSettingItem(
                        viewType = 3,
                        background = 20,
                        iconResourceLeft = 0,
                        title = getString(R.string.setting_arrays),
                        description = ""
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_SHOW_INFO,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.baseline_person_outline_24,
                        title = getString(R.string.info_myself),
                        description = getString(R.string.info_myself_edit)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_NOTIFICATION,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.icon_bell_main,
                        title = getString(R.string.notification_arrays),
                        description = getString(R.string.notification_arrays_text)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_PAYMENT,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_payment_svgrepo_com,
                        title = getString(R.string.yourpayment),
                        description = getString(R.string.textInfoTextPayment)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_CHANG_PASS,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_icon_change_pass_setting,
                        title = getString(R.string.ChangePassSetting),
                        description = getString(R.string.LanguageSetting2)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_CHANG_LANGUAGE,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_internet_language_settings_ver3,
                        title = getString(R.string.LanguageSetting),
                        description = getString(R.string.LanguageSetting_text)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_CHANG_THEME,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_group_moon_setting,
                        title = getString(R.string.theme),
                        description = getString(R.string.Light)
                    ), createSettingItem(
                        viewType = 3,
                        background = 20,
                        iconResourceLeft = 0,
                        title = getString(R.string.help_arrays),
                        description = ""
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_ABOUT_US,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_icon_logo_app,
                        title = getString(R.string.byRoyal),
                        description = getString(R.string.infoRoyal)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_CHANG_HELP,
                        viewType = 1,
                        background = R.drawable.background_setting_item,
                        iconResourceLeft = R.drawable.ic_helps_white_menu,
                        title = getString(R.string.question_bookmard2),
                        description = getString(R.string.question_help1)
                    ), createSettingItem(
                        tag = AppConstant.TAG_SETTING_LOGOUT,
                        viewType = 3,
                        background = 20,
                        iconResourceLeft = 0,
                        title = getString(R.string.logout_arrays),
                        description = "",
                        isVisible = false
                    )
                )
            )
        }
    }

    private fun navigateFragment(id: Int) {
        findNavController().navigate(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listSetting.clear()
    }
}