package com.mboudraa.thescore.ui.detail

import com.mboudraa.thescore.core.State
import com.mboudraa.thescore.core.StateMachine
import com.mboudraa.thescore.core.Team
import com.mboudraa.thescore.core.TeamState
import com.mboudraa.thescore.ui.BaseFragmentViewModel

class TeamViewModel(stateMachine: StateMachine) : BaseFragmentViewModel<Team>(stateMachine) {

    override fun computeViewState(state: State): Team? {
        return when (state) {
            is TeamState -> state.team
            else -> null
        }
    }

}