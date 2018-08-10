package com.mboudraa.thescore.ui.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mboudraa.thescore.R
import com.mboudraa.thescore.core.Team
import com.mboudraa.thescore.ui.bindView
import com.mboudraa.thescore.ui.detail.TeamView.TeamPagerAdapter.TeamTab.PLAYERS
import com.mboudraa.thescore.ui.detail.TeamView.TeamPagerAdapter.TeamTab.STATS


class TeamView : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val toolbar by bindView<Toolbar>(R.id.team_toolbar)
    private val viewPager by bindView<ViewPager>(R.id.team_viewpager)
    private val tabLayout by bindView<TabLayout>(R.id.team_tablayout)

    fun render(team: Team) {
        toolbar.title = team.name
        viewPager.adapter = TeamPagerAdapter(context, team)
        viewPager.currentItem = 0
        tabLayout.setupWithViewPager(viewPager)
    }


    class TeamPagerAdapter(private val context: Context,
                           private val team: Team) : PagerAdapter() {

        private val layoutInflater = LayoutInflater.from(context)

        enum class TeamTab {
            STATS, PLAYERS
        }

        private val tabs = TeamTab.values()

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        override fun getCount(): Int = tabs.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val tab = tabs[position]
            return when (tab) {
                STATS -> (layoutInflater.inflate(R.layout.team_stats, container, false) as TeamStatsView).apply {
                    render(team)
                }
                PLAYERS -> (layoutInflater.inflate(R.layout.roster, container, false) as RosterView).apply {
                    render(team)
                }
//                PLAYERS -> (layoutInflater.inflate(R.layout.roster, container, false) as RecyclerView).apply {
//                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                    setHasFixedSize(true)
//                    adapter = PlayerRecyclerViewAdapter(LayoutInflater.from(context), team.players)
//                }
            }.apply(container::addView)
        }

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View)
        }

        override fun getPageTitle(position: Int): CharSequence {
            val tab = tabs[position]
            return when (tab) {
                STATS -> context.getString(R.string.team_detail_tab_stats)
                PLAYERS -> context.getString(R.string.team_detail_tab_players)
            }
        }

    }

}