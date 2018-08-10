package com.mboudraa.thescore.core

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


sealed class State {
    open fun doOnEnter(stateMachine: StateMachine) {}

    interface CanGoBack {
        fun back(stateMachine: StateMachine)
    }
}

class IdleState(private val teamRepository: TeamRepository) : State() {

    fun loadTeams(stateMachine: StateMachine) {
        stateMachine.nextState(LoadingState(teamRepository, Sort.NAME))
    }

    override fun toString() = "IdleState"
}

class LoadingState(private val teamRepository: TeamRepository,
                   val sort: Sort) : State() {

    override fun doOnEnter(stateMachine: StateMachine) {
        launch(CoroutineContextProvider.UI) {
            val teams = async(CoroutineContextProvider.IO) { teamRepository.getTeams(sort) }
            stateMachine.nextState(TeamListState(teamRepository, teams.await(), sort))
        }
    }

    override fun toString() = "LoadingState(sort=$sort)"
}

class TeamListState(private val teamRepository: TeamRepository,
                    val teams: List<Team>,
                    val sort: Sort) : State() {

    fun sort(stateMachine: StateMachine, sort: Sort) {
        stateMachine.nextState(LoadingState(teamRepository, sort))
    }

    fun selectTeam(stateMachine: StateMachine, team: Team) {
        stateMachine.nextState(TeamState(teamRepository, teams, sort, team))
    }

    override fun toString() = "TeamListState(sort=$sort, teams=$teams)"
}

class TeamState(private val teamRepository: TeamRepository,
                private val teams: List<Team>,
                private val sort: Sort,
                val team: Team) : State(), State.CanGoBack {

    override fun back(stateMachine: StateMachine) {
        stateMachine.nextState(TeamListState(teamRepository, teams, sort))
    }

    override fun toString() = "TeamState(team=$team)"
}
