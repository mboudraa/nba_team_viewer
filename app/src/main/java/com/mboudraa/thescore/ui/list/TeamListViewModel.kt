package com.mboudraa.thescore.ui.list

import com.mboudraa.thescore.core.*
import com.mboudraa.thescore.ui.BaseFragmentViewModel

class TeamListViewModel(stateMachine: StateMachine) : BaseFragmentViewModel<TeamsViewState>(stateMachine) {

    override fun computeViewState(state: State): TeamsViewState? {
        return when (state) {
            is LoadingState -> TeamsViewState(isLoading = true, teams = listOf(), sort = state.sort)
            is TeamListState -> TeamsViewState(isLoading = false, teams = state.teams, sort = state.sort)
            else -> null
        }
    }

    fun sort(sort: Sort) {
        stateMachine.currentStateAs<TeamListState>()?.sort(stateMachine, sort)
    }

    fun selectTeam(team: Team) {
        stateMachine.currentStateAs<TeamListState>()?.selectTeam(stateMachine, team)
    }

}