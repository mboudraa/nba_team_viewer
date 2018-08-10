package com.mboudraa.thescore.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.mboudraa.thescore.R
import com.mboudraa.thescore.core.*
import java.util.*


class StateNavController(private val navController: NavController) {

    private val deque = ArrayDeque<Int>()

    fun navigate(navDirections: NavDirections) {
        val destinationId = navDirections.actionId

        if (deque.isEmpty()) {
            deque.push(destinationId)
        }

        if (navController.currentDestination.id == destinationId) return

        if (deque.contains(destinationId)) {
            deque.popUntil(destinationId)
            navController.popBackStack(destinationId, false)
        } else {
            deque.push(destinationId)
            navController.navigate(destinationId)
        }
    }

    fun navigateUp(stateMachine: StateMachine): Boolean {
        val currentState = stateMachine.currentState
        return when (currentState) {
            is State.CanGoBack -> when {
                deque.size > 1 -> {
                    currentState.back(stateMachine)
                    true
                }
                else -> false
            }
            else -> false
        }
    }

    private fun <E> Deque<E>.popUntil(element: E) {
        while (peek() != element && isNotEmpty()) {
            pop()
        }
    }
}

fun State.toNavDirections(): NavDirections {
    return when (this) {
        is IdleState, is LoadingState, is TeamListState -> object : NavDirections {
            override fun getArguments(): Bundle? = null
            override fun getActionId(): Int = R.id.teamsListFragment
        }

        is TeamState -> object : NavDirections {
            override fun getArguments(): Bundle? = null
            override fun getActionId(): Int = R.id.teamDetailFragment
        }
    }
}