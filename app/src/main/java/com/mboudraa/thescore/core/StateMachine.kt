package com.mboudraa.thescore.core

class StateMachine(defaultState: State) {

    private val listeners = arrayListOf<OnStateChangedListener>()

    var currentState: State = defaultState
        private set(value) {
            field = value
            listeners.forEach { it.onStateChanged(value) }
            value.doOnEnter(this)
        }

    fun addOnStateChangedListener(listener: OnStateChangedListener) {
        listener.onStateChanged(currentState)
        listeners.add(listener)
    }

    fun removeOnStateChangedListener(listener: OnStateChangedListener) {
        listeners.remove(listener)
    }

    fun nextState(state: State) {
        currentState = state
    }

    interface OnStateChangedListener {
        fun onStateChanged(state: State)
    }
}

inline fun <reified STATE : State> StateMachine.currentStateAs() = currentState as? STATE
