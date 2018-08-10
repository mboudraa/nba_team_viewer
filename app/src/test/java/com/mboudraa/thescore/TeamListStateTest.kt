package com.mboudraa.thescore

import com.google.common.truth.Truth.assertThat
import com.mboudraa.thescore.core.*
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TeamListStateTest {

    @get:Rule val rule = CoroutineContextRule()

    val teamRepository = mock<TeamRepository>()

    @Test
    fun `should go to LoadingState when sort`() {
        val state = TeamListState(teamRepository, listOf(), Sort.NAME)
        val stateMachine = StateMachine(state)

        state.sort(stateMachine, Sort.WINS)

        assertThat(stateMachine.currentState).isInstanceOf(LoadingState::class.java)
        assertThat(stateMachine.currentStateAs<LoadingState>()?.sort).isEqualTo(Sort.WINS)
    }

    @Test
    fun `should go to teamState when selectTeam`() {

        val team = Team(id = 1, wins = 45, losses = 20, name = "Boston Celtics", players = listOf(
                Player(id = 37729, firstName = "Kadeem", lastName = "Allen", position = "SG", number = 45),
                Player(id = 30508, firstName = "Aron", lastName = "Baynes", position = "C", number = 46),
                Player(id = 30847, firstName = "Jabari", lastName = "Bird", position = "SG", number = 26)
        ))
        val teams = listOf(team)

        val state = TeamListState(teamRepository, teams, Sort.NAME)
        val stateMachine = StateMachine(state)

        state.selectTeam(stateMachine, team)

        assertThat(stateMachine.currentState).isInstanceOf(TeamState::class.java)
        assertThat(stateMachine.currentStateAs<TeamState>()?.team).isEqualTo(team)
    }
}