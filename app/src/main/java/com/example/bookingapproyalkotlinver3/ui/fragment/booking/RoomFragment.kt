package com.example.bookingapproyalkotlinver3.ui.fragment.booking

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookingapproyalkotlinver3.R
import com.example.bookingapproyalkotlinver3.base.BaseFragment
import com.example.bookingapproyalkotlinver3.constant.AppConstant
import com.example.bookingapproyalkotlinver3.data.model.hotel.Room
import com.example.bookingapproyalkotlinver3.data.util.Resource
import com.example.bookingapproyalkotlinver3.data.util.view.decimalFormat
import com.example.bookingapproyalkotlinver3.databinding.FragmentRoomBinding
import com.example.bookingapproyalkotlinver3.ui.adapter.ConvenientAdapter
import com.example.bookingapproyalkotlinver3.ui.adapter.ImageAutoSliderAdapter
import com.example.bookingapproyalkotlinver3.ui.customview.autoimage.IndicatorView.animation.type.IndicatorAnimationType
import com.example.bookingapproyalkotlinver3.ui.customview.autoimage.SliderAnimations
import com.example.bookingapproyalkotlinver3.ui.customview.autoimage.SliderView
import com.example.bookingapproyalkotlinver3.viewModel.MainViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : BaseFragment<FragmentRoomBinding>(FragmentRoomBinding::inflate) {
    private lateinit var idRoom: String
    private val viewModel: MainViewModel by viewModels()
    private var cancelBooking: Boolean = false
    private var ageChildren: Int = 0

    override fun initView() {
        binding.collapseToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        binding.collapseToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener(function = { _, verticalOffset ->
            if ((binding.collapseToolbarLayout.height + verticalOffset) < (2 * ViewCompat.getMinimumHeight(
                    binding.collapseToolbarLayout
                ))
            ) {
                binding.toolbar.navigationIcon?.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    ), PorterDuff.Mode.SRC_ATOP
                )
            } else {
                binding.toolbar.navigationIcon?.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    ), PorterDuff.Mode.SRC_ATOP
                )
            }
        }))
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })
    }

    override fun initOnClickListener() {

    }

    override fun observeLiveData() {
        viewModel.roomResponseMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        binding.progressBar.visibility = View.GONE
                        loadData(it)
                    }
                }

                is Resource.Loading -> {
                    it.message?.let {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "UseRequireInsteadOfGet")
    private fun loadData(room: Room) {
        val imageAutoAdapter = ImageAutoSliderAdapter(room.images)
        binding.imageItem.setSliderAdapter(imageAutoAdapter)
        binding.imageItem.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)

        binding.imageItem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageItem.autoCycleDirection =
            SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        binding.imageItem.indicatorSelectedColor = Color.WHITE
        binding.imageItem.indicatorUnselectedColor = Color.GRAY
        binding.imageItem.scrollTimeInSec = 4
        binding.imageItem.startAutoCycle()

        binding.collapseToolbarLayout.title = room.name
        binding.countImage.text = room.images.size.toString()
        val totalPersons = room.maxTreEm + room.maxNguoiLon
        val countPersonText =
            getString(R.string.Stay) + " " + totalPersons + " " + getString(R.string.textBest3)
        binding.tvCountPerson.text = countPersonText

        binding.text2.text =
            "• " + getString(R.string.Maximum_occupancy_of) + " " + (room.maxTreEm + room.maxNguoiLon) + " " + getString(
                R.string.textBest3
            )
        binding.text1.text =
            "• " + getString(R.string.Allowed) + " " + room.maxNguoiLon + " " + getString(R.string.adult)
        binding.text3.text =
            "• " + getString(R.string.Up_to) + " " + room.maxTreEm + " " + getString(R.string.children_allowed)

        for (bedroom in room.bedroom) {
            val content = "${bedroom.name}, "
            binding.tvSoGiuong.append(content)
        }

        binding.tvDientich.text = "${room.dienTich} m²"
        binding.tvSoPhong.text =
            " ${getString(R.string.There_are)} ${room.soPhong} ${getString(R.string.other_rooms_like_this_one)}"
        binding.tvGia.text = "${decimalFormat.format(room.price)} đ"
        binding.text4.text =
            " ${getString(R.string.Children_over_the_age_of)} $ageChildren ${getString(R.string.will_be_counted_as_adults)}"
        binding.contentMota.text = room.mota
        val convenientAdapter = ConvenientAdapter(room.tienNghiPhong)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = convenientAdapter

        if (cancelBooking) {
            binding.canceltrue.visibility = View.VISIBLE
        } else {
            binding.cancelfalse.visibility = View.VISIBLE
        }
    }

    override fun initData() {
        arguments?.let {
            idRoom = it.getString(AppConstant.ROOM_EXTRA, "")
            cancelBooking = it.getBoolean(AppConstant.CANCEL_BOOKING, false)
            ageChildren = it.getInt(AppConstant.AGE_CHILDREN, 0)
            Handler().postDelayed({
                viewModel.getRoomById(it.getString(AppConstant.ROOM_EXTRA, ""))
            }, 500)
        }
    }

}