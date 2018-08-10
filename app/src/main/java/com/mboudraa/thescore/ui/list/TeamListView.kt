package com.mboudraa.thescore.ui.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mboudraa.thescore.R
import com.mboudraa.thescore.core.Sort
import com.mboudraa.thescore.core.Team
import com.mboudraa.thescore.ui.bindView

typealias OnTeamSelectedListener = (Team) -> Unit

class TeamListView : CoordinatorLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var listener: OnTeamSelectedListener? = null

    private val recyclerView by bindView<RecyclerView>(R.id.team_list_recyclerview)
    private val progressbar by bindView<ProgressBar>(R.id.team_list_progressbar)
    val toolbar by bindView<Toolbar>(R.id.team_list_toolbar)

    private val adapter = Adapter(LayoutInflater.from(context))

    override fun onFinishInflate() {
        super.onFinishInflate()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@TeamListView.adapter
        }
        toolbar.title = context.getString(R.string.team_list_title)
    }

    fun render(viewState: TeamsViewState) {
        progressbar.visibility = if (viewState.isLoading) View.VISIBLE else GONE
        adapter.setData(viewState.teams)
    }


    fun setOnTeamSelectedListener(listener: OnTeamSelectedListener) {
        this.listener = listener
    }

    private inner class Adapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<Adapter.TeamViewHolder>() {

        private val teams = arrayListOf<Team>()

        override fun getItemCount(): Int = teams.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
            return TeamViewHolder(inflater.inflate(R.layout.team_list_content, parent, false) as TeamCell)
        }

        override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
            holder.bind(teams[position])
        }

        fun setData(data: Collection<Team>) {
            teams.clear()
            teams.addAll(data)
            notifyDataSetChanged()
        }

        private inner class TeamViewHolder(private val view: TeamCell) : RecyclerView.ViewHolder(view) {
            fun bind(team: Team) = view.apply { bind(team) }.setOnClickListener { listener?.invoke(team) }
        }
    }

}


class TeamCell : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val nameTextView by bindView<TextView>(R.id.team_cell_name)
    private val winsTextView by bindView<TextView>(R.id.team_cell_wins)
    private val lossesTextView by bindView<TextView>(R.id.team_cell_losses)


    fun bind(team: Team) {
        nameTextView.text = team.name
        winsTextView.text = context.getString(R.string.team_list_content_wins, team.wins)
        lossesTextView.text = context.getString(R.string.team_list_content_losses, team.losses)
    }
}

class TeamsViewState(val teams: List<Team>,
                     val isLoading: Boolean,
                     val sort: Sort)
