package edu.skku.map.capstone.view.mycafe

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import edu.skku.map.capstone.R
import edu.skku.map.capstone.databinding.FragmentMyCafeBinding
import edu.skku.map.capstone.models.cafe.Cafe
import edu.skku.map.capstone.models.user.User
import org.json.JSONArray
import org.json.JSONObject

import android.util.Log
class MyCafeFragment : Fragment() {
    private var _binding: FragmentMyCafeBinding? = null
    private val binding get() = _binding!!

    // #1. Piechart
    private lateinit var topCategories: List<Map.Entry<String, Int>>
    // #2. 즐겨찾기한 카페
    private lateinit var favoriteListAdapter: FavoriteListAdapter
    // #3. 이런곳은 어떤가요?



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyCafeBinding.inflate(inflater, container, false)
        initUI()
        observeDataList()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        User.refresh()
        Log.d("myCafe", "onResume")
    }

    private fun initUI() {
        // #2. 즐겨찾기한 카페
        favoriteListAdapter = FavoriteListAdapter(requireContext())
        binding.favoriteCafeListRV.adapter = favoriteListAdapter
        initPieChart()
//        setPieChartContents()
    }

    private fun observeDataList() {
        // #2. 즐겨찾기한 카페
        User.getInstance().favorites.observe(viewLifecycleOwner) {
            favoriteListAdapter.updateCafeList(it)
            updatePieChart(it)
        }

    }
    private fun generateExtremeDummyCafes(): ArrayList<Cafe> {
        val dummyCafes = ArrayList<Cafe>()
        val scores = listOf(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.5)
        for (i in scores.indices) {
            val score = scores[i]
            val jsonObject = JSONObject().apply {
                put("id", i + 1)
                put("cafe_name", "Dummy Cafe ${i + 1}")
                put("road_address_name", "Dummy Road Address ${i + 1}")
                put("phone", "010-1234-567${i + 1}")
                put("latitude", 37.0 + i)
                put("longitude", 127.0 + i)
                put("place_url", "http://dummycafe${i + 1}.com")
                put("capacity", (3..5).random().toDouble())
                put("power_socket", (3..5).random().toDouble())
                put("quiet", (3..5).random().toDouble())
                put("wifi", (3..5).random().toDouble())
                put("tables", (3..5).random().toDouble())
                put("toilet", (3..5).random().toDouble())
                put("bright", (3..5).random().toDouble())
                put("clean", (3..5).random().toDouble())
                put("capacity_cnt", 1)
                put("power_socket_cnt", 1)
                put("quiet_cnt", 1)
                put("wifi_cnt", 1)
                put("tables_cnt", 1)
                put("toilet_cnt", 1)
                put("bright_cnt", 1)
                put("clean_cnt", 1)
                put("reviews", JSONArray())
            }
            val cafe = Cafe(jsonObject)
            dummyCafes.add(cafe)
        }
        return dummyCafes
    }


    private fun analyzeFavorites(favoriteCafes: List<Cafe>): Map<String, Int> {
        val scoreThreshold = 3.5
        val counts = mutableMapOf(
            "powerSocket" to 0,
            "capacity" to 0,
            "quiet" to 0,
            "wifi" to 0,
            "tables" to 0,
            "toilet" to 0,
            "bright" to 0,
            "clean" to 0
        )

        for (cafe in favoriteCafes) {
            if (cafe.powerSocket >= scoreThreshold) counts["powerSocket"] = counts["powerSocket"]!! + 1
            if (cafe.capacity >= scoreThreshold) counts["capacity"] = counts["capacity"]!! + 1
            if (cafe.quiet >= scoreThreshold) counts["quiet"] = counts["quiet"]!! + 1
            if (cafe.wifi >= scoreThreshold) counts["wifi"] = counts["wifi"]!! + 1
            if (cafe.tables >= scoreThreshold) counts["tables"] = counts["tables"]!! + 1
            if (cafe.toilet >= scoreThreshold) counts["toilet"] = counts["toilet"]!! + 1
            if (cafe.bright >= scoreThreshold) counts["bright"] = counts["bright"]!! + 1
            if (cafe.clean >= scoreThreshold) counts["clean"] = counts["clean"]!! + 1
        }



        // 내림차순으로 정렬
        val sortedCounts = counts.entries.sortedByDescending { it.value }

        topCategories = sortedCounts.take(2)  // 상위 2개 항목 저장

        // 상위 3개 항목만 반환
        val result = linkedMapOf<String, Int>()
        for ((key, value) in sortedCounts.take(3)) {
            result[key] = value
        }

        return result
    }


    private fun initPieChart(){
//        binding.pieChart.setUsePercentValues(true)
//        val counts = anaylzeFavorites(User.getInstance().favorites)
//        val pieEntries = mutableListOf<PieEntry>()
//
//        counts.forEach { (key, value) ->
//            pieEntries.add(PieEntry(value.toFloat(), siwtchToKor(key)))
//        }
//
//        val pieColors = listOf(
//            resources.getColor(R.color.yellow, null),
//            resources.getColor(R.color.orange, null),
//            resources.getColor(R.color.green, null)
//        )
//
//        val dataSet = PieDataSet(pieEntries, "preferences")
//        dataSet.colors = pieColors
//        dataSet.setDrawValues(false) // 값 표시 비활성화
//
//        binding.pieChart.apply {
//            data = PieData(dataSet)
//            description.isEnabled = false
//            legend.isEnabled = false
//            isRotationEnabled = false
//            holeRadius = 70f
//            setEntryLabelColor(Color.WHITE) // 안 글씨 색
////            setDrawRoundedSlices(true) // 모양 둥글게
//            setTouchEnabled(false)
//            setDrawEntryLabels(true) // 항목 라벨 숨기기
//            setDrawCenterText(true)
//            animateY(1200, Easing.EaseInOutCubic)
//            animate()
//        }
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            isRotationEnabled = false
            holeRadius = 70f
            setEntryLabelColor(Color.WHITE) // 안 글씨 색
            setTouchEnabled(false)
            setDrawEntryLabels(true) // 항목 라벨 숨기기
            setDrawCenterText(true)
            animateY(1200, Easing.EaseInOutCubic)
        }
    }

    private fun updatePieChart(favoriteCafes: List<Cafe>) {
        val counts = analyzeFavorites(ArrayList(favoriteCafes))
        val pieEntries = mutableListOf<PieEntry>()

        counts.forEach { (key, value) ->
            pieEntries.add(PieEntry(value.toFloat(), switchToKor(key)))
        }

        val pieColors = listOf(
            resources.getColor(R.color.yellow, null),
            resources.getColor(R.color.orange, null),
            resources.getColor(R.color.green, null)
        )

        val dataSet = PieDataSet(pieEntries, "preferences")
        dataSet.colors = pieColors
        dataSet.setDrawValues(false) // 값 표시 비활성화

        binding.pieChart.data = PieData(dataSet)
        binding.pieChart.invalidate() // refresh the pie chart

        setPieChartContents()
    }

    private fun switchToKor(category: String): String {
        val categoryText = when (category) {
            "powerSocket" -> "콘센트"
            "capacity" -> "공간"
            "quiet" -> "분위기"
            "wifi" -> "와이파이"
            "tables" -> "좌석"
            "toilet" -> "화장실"
            "bright" -> "밝기"
            "clean" -> "위생"
            else -> ""
        }
        return categoryText
    }

    private fun setPieChartContents() {
        val (category, count) = topCategories[0]
        val categoryText = when (category) {
            "powerSocket" -> "충전이 가능해요"
            "capacity" -> "넓고"
            "quiet" -> "조용해요"
            "wifi" -> "와이파이가 빨라요"
            "tables" -> "책상이 편해요"
            "toilet" -> "화장실이 쾌적해요"
            "bright" -> "매장이 밝아요"
            "clean" -> "깨끗해요"
            else -> ""
        }

        val categoryImageRes = when (category) {
            "powerSocket" -> R.drawable.icon_powersocket_resize // Replace with actual image resource
            "capacity" -> R.drawable.icon_capacity_resize // Replace with actual image resource
            "quiet" -> R.drawable.icon_quiet_resize // Replace with actual image resource
            "wifi" -> R.drawable.icon_wifi_resize // Replace with actual image resource
            "tables" -> R.drawable.icon_table_resize // Replace with actual image resource
            "toilet" -> R.drawable.icon_toilet_resize // Replace with actual image resource
            "bright" -> R.drawable.icon_bright_resize // Replace with actual image resource
            "clean" -> R.drawable.icon_clean_resize // Replace with actual image resource
            else -> R.drawable.defaultcafepin // Replace with default image resource
        }

        binding.analyzeText.setText(categoryText)
        binding.analyzeCnt.setText("$count 개")
        binding.anaylzeImage.setImageResource(categoryImageRes)
        binding.myCafeContent1.setText("통계에 따르면 ${User.getInstance().userName}님이 선호하시는 카페는")
        // 추천텍스트
        val categoryDescriptions = mapOf(
            "powerSocket" to "충전이 가능하고", // 8
            "capacity" to "넓고",
            "quiet" to "조용하고",
            "wifi" to "와이파이가 빠르고",
            "tables" to "편하고",
            "toilet" to "화장실이 쾌적하고",
            "bright" to "밝고",
            "clean" to "깨끗하고"
        )

        val categoryAdjectives = mapOf(
            "powerSocket" to "충전 가능한",
            "capacity" to "넓은",
            "quiet" to "조용한",
            "wifi" to "와이파이가 빠른", // 8
            "tables" to "편한",
            "toilet" to "화장실이 쾌적한",
            "bright" to "밝은",
            "clean" to "깨끗한"
        )
        val originalText = "${categoryDescriptions[topCategories[0].key]}, ${categoryAdjectives[topCategories[1].key]} 카페에요"
        val processedText = if (originalText.length > 20) {
            originalText.replace(",", ",\n")
        } else {
            originalText
        }
        binding.preferCafeTxt.text = processedText
    }


}