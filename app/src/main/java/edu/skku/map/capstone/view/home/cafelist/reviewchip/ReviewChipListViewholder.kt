package edu.skku.map.capstone.view.home.cafelist.reviewchip

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import edu.skku.map.capstone.R
import edu.skku.map.capstone.databinding.ItemReviewChipBinding

class ReviewChipListViewholder(val context:Context, var binding:ItemReviewChipBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(reviewText: String){
            when(reviewText){
                "bright" -> {
                    binding.reviewChipTV.text ="밝아요"
                    binding.iconIV.setImageResource(R.drawable.icon_bright_faded)
                    }
                "clean" -> {
                    binding.reviewChipTV.text ="깨끗해요"
                    binding.iconIV.setImageResource(R.drawable.icon_clean_faded)
                    }
                "quiet" -> {
                    binding.reviewChipTV.text ="조용해요"
                    binding.iconIV.setImageResource(R.drawable.icon_quiet_faded)
                }
                "capacity" -> {
                    binding.reviewChipTV.text ="넓어요"
                    binding.iconIV.setImageResource(R.drawable.icon_capacity_faded)

                }
                "powerSocket" -> {
                    binding.reviewChipTV.text ="콘센트가 많아요"
                    binding.iconIV.setImageResource(R.drawable.icon_powersocket_faded)

                }
                "wifi" -> {
                    binding.reviewChipTV.text ="와이파이가 빨라요"
                    binding.iconIV.setImageResource(R.drawable.icon_wifi_faded)

                }
                "tables" -> {
                    binding.reviewChipTV.text ="책상이 넓어요"
                    binding.iconIV.setImageResource(R.drawable.icon_tables_faded)

                }
                "toilet" -> {
                    binding.reviewChipTV.text ="화장실이 쾌적해요"
                    binding.iconIV.setImageResource(R.drawable.icon_toilet_faded)

                }
                else-> Unit
            }

    }
}