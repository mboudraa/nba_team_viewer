package com.mboudraa.thescore.ui.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mboudraa.thescore.R
import com.mboudraa.thescore.core.Player
import com.mboudraa.thescore.core.Team
import com.mboudraa.thescore.ui.bindView

class RosterView : LinearLayout {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val header by bindView<RosterRow>(R.id.roster_header)

    private val recyclerView by bindView<RecyclerView>(R.id.roster_recyclerview) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setHasFixedSize(true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        header.render(context.getString(R.string.team_detail_roster_title_number),
                context.getString(R.string.team_detail_roster_title_name),
                context.getString(R.string.team_detail_roster_title_position))
    }

    fun render(team: Team) {
        recyclerView.adapter = PlayerRecyclerViewAdapter(LayoutInflater.from(context), team.players)
    }
}

class PlayerRecyclerViewAdapter(private val inflater: LayoutInflater,
                                private val players: List<Player>) : RecyclerView.Adapter<PlayerRecyclerViewAdapter.PlayerViewHolder>() {


    override fun getItemCount(): Int = players.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(inflater.inflate(R.layout.roster_row, parent, false) as RosterRow)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(players[position])
    }

    class PlayerViewHolder(private val view: RosterRow) : RecyclerView.ViewHolder(view) {
        fun bind(player: Player) = view.apply {
            render("${player.number}",
                    "${player.firstName} ${player.lastName}",
                    player.position)
        }
    }
}

class RosterRow : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    private val numberTextView by bindView<TextView>(R.id.roster_row_nb)
    private val nameTextView by bindView<TextView>(R.id.roster_row_name)
    private val positionTextView by bindView<TextView>(R.id.roster_row_pos)

    fun render(number: CharSequence, name: CharSequence, position: CharSequence) {
        numberTextView.text = number
        nameTextView.text = name
        positionTextView.text = position
    }
}