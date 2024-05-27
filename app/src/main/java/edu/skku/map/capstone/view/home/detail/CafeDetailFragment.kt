package edu.skku.map.capstone.view.home.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import edu.skku.map.capstone.MainActivity
import edu.skku.map.capstone.databinding.FragmentCafeDetailBinding
import edu.skku.map.capstone.models.cafe.Cafe
import edu.skku.map.capstone.view.ReviewActivity
import edu.skku.map.capstone.view.home.HomeFragment

class CafeDetailFragment(private val cafe: Cafe, private val reviewingCafe: MutableLiveData<Cafe>, private val phase: MutableLiveData<Int>, private val pullDownBottomSheet: MutableLiveData<Boolean>) : Fragment() {
    private var _binding: FragmentCafeDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCafeDetailBinding.inflate(inflater, container, false)
        initUI()
        handleClickListeners()
        return binding.root
    }

    private fun initUI() {

        binding.detailCafeNameTV.text = cafe.cafeName
        binding.detailCafeName2TV.text = cafe.cafeName
        binding.detailAddressTV.text = cafe.address
        binding.detailPhoneTV.text = cafe.phone
        binding.detailDistanceTV.text = cafe.distance.toString()+"KM"

        val ratingLLs = listOf(
            binding.ratingCapacityLL,
            binding.ratingBrightLL,
            binding.ratingCleanLL,
            binding.ratingWifiLL,
            binding.ratingQuietLL,
            binding.ratingTablesLL,
            binding.ratingPowerSocketLL,
            binding.ratingToiletLL
        )

        val ratingCVs = listOf(
            binding.ratingbarCapacityScoreCV,
            binding.ratingbarBrightScoreCV,
            binding.ratingbarCleanScoreCV,
            binding.ratingbarWifiScoreCV,
            binding.ratingbarQuietScoreCV,
            binding.ratingbarTablesScoreCV,
            binding.ratingbarPowersocketScoreCV,
            binding.ratingbarToiletScoreCV
        )

        val ratingTVs = listOf(
            binding.ratingCapacityTV,
            binding.ratingBrightTV,
            binding.ratingCleanTV,
            binding.ratingQuietTV,
            binding.ratingWifiTV,
            binding.ratingTablesTV,
            binding.ratingPowersocketTV,
            binding.ratingToiletTV
        )

        val ratings = listOf(
            cafe.capacity,
            cafe.bright,
            cafe.clean,
            cafe.quiet,
            cafe.wifi,
            cafe.tables,
            cafe.powerSocket,
            cafe.toilet
        )

        val ratingCnts = listOf(
            cafe.capacityCnt,
            cafe.brightCnt,
            cafe.cleanCnt,
            cafe.quietCnt,
            cafe.wifiCnt,
            cafe.tablesCnt,
            cafe.powerSocketCnt,
            cafe.toiletCnt
        )

        for(i in 0..7) {
            ratingTVs[i].text = ratings[i].toString()
            val layoutParams = ratingCVs[i].layoutParams as ViewGroup.LayoutParams
            layoutParams.width = ratingBarLength(ratings[i])
            ratingCVs[i].layoutParams = layoutParams
        }
    }

    private fun handleClickListeners() {
        binding.backBtn.setOnClickListener {
            val parentFragment: HomeFragment = parentFragment as HomeFragment
            parentFragment.onCafeDetailClosed()
            (activity as MainActivity).reviewingCafe.postValue(null)
        }
        binding.detailReviewBtn.setOnClickListener {
//            val intent = Intent(requireActivity(), ReviewActivity::class.java)
//            startActivity(intent)
            Log.d("dialog", "reviewBtn clicked")
            reviewingCafe.postValue(cafe)
            phase.postValue(1)
        }
        binding.detailMapBtn.setOnClickListener {
            pullDownBottomSheet.postValue(true)
        }
        binding.detailLikeBtn.setOnClickListener {

        }
    }

    private fun ratingBarLength(rating:Double):Int = (rating/5.0*578).toInt()
}