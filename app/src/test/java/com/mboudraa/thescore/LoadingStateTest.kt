package com.mboudraa.thescore

import com.google.common.truth.Truth.assertThat
import com.mboudraa.thescore.core.*
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoadingStateTest {

    @get:Rule val rule = CoroutineContextRule()

    @Test
    fun `should go to TeamListState once teams are loaded()`() {
        val teamRepository = mock<TeamRepository> {
            onBlocking { getTeams(anyOrNull()) } doReturn listOf<Team>()
        }

        val loadingState = LoadingState(teamRepository, Sort.NAME)
        val stateMachine = StateMachine(loadingState)
        loadingState.doOnEnter(stateMachine)

        assertThat(stateMachine.currentState).isInstanceOf(TeamListState::class.java)
    }

}

