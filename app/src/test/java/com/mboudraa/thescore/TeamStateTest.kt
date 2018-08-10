package com.mboudraa.thescore

import com.google.common.truth.Truth.assertThat
import com.mboudraa.thescore.core.*
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TeamStateTest {

    @get:Rule val rule = CoroutineContextRule()


    @Test
    fun `should go back to TeamListState`() {
        val teamRepository = mock<TeamRepository>()

        val team = Team(id = 1, wins = 45, losses = 20, name = "Boston Celtics", players = listOf(
                Player(id = 37729, firstName = "Kadeem", lastName = "Allen", position = "SG", number = 45),
                Player(id = 30508, firstName = "Aron", lastName = "Baynes", position = "C", number = 46),
                Player(id = 30847, firstName = "Jabari", lastName = "Bird", position = "SG", number = 26)
        ))
        val teams = listOf(team)

        val state = TeamState(teamRepository, teams, Sort.LOSSES, team)
        val stateMachine = StateMachine(state)

        state.back(stateMachine)

        assertThat(stateMachine.currentState).isInstanceOf(TeamListState::class.java)
        assertThat(stateMachine.currentStateAs<TeamListState>()?.teams).isEqualTo(teams)
        assertThat(stateMachine.currentStateAs<TeamListState>()?.sort).isEqualTo(Sort.LOSSES)
    }
}