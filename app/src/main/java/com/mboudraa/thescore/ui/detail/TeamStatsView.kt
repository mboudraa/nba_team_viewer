package com.mboudraa.thescore.ui.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mboudraa.thescore.R
import com.mboudraa.thescore.core.Team
import com.mboudraa.thescore.ui.bindView

class TeamStatsView : LinearLayout {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val pieChart by bindView<PieChart>(R.id.team_stats_piechart) {
        setTouchEnabled(false)
    }


    fun render(team: Team) {
        val dataset = PieDataSet(listOf(
                PieEntry(team.wins.toFloat(), context.getString(R.string.team_detail_stats_wins)),
                PieEntry(team.losses.toFloat(), context.getString(R.string.team_detail_stats_losses))
        ), "Stats").apply {
            setColors(intArrayOf(R.color.colorPrimary, R.color.colorAccent), context)
            setValueFormatter { value, _, _, _ -> value.toInt().toString() }
            valueTextColor = ResourcesCompat.getColor(resources, android.R.color.white, null)
            valueTextSize = 16f
        }
        pieChart.apply {
            data = PieData(dataset)
            legend.isEnabled = false
            animateY(500)
        }
    }
}