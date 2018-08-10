package com.mboudraa.thescore

import com.google.common.truth.Truth.assertThat
import com.mboudraa.thescore.core.IdleState
import com.mboudraa.thescore.core.LoadingState
import com.mboudraa.thescore.core.StateMachine
import com.mboudraa.thescore.core.TeamRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IdleStateTest {

    @get:Rule val rule = CoroutineContextRule()

    @Test
    fun `should go to LoadingState when call loadTeams()`() {
        val teamRepository = mock<TeamRepository>()

        val idleState = IdleState(teamRepository)
        val stateMachine = StateMachine(idleState)
        idleState.loadTeams(stateMachine)

        assertThat(stateMachine.currentState).isInstanceOf(LoadingState::class.java)
    }
}