package com.mboudraa.thescore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mboudraa.thescore.core.State
import com.mboudraa.thescore.core.StateMachine

abstract class BaseFragmentViewModel<VIEW_STATE>(protected val stateMachine: StateMachine) : ViewModel(), StateMachine.OnStateChangedListener {

    val viewStateLiveData: LiveData<VIEW_STATE> = MutableLiveData()

    init {
        stateMachine.addOnStateChangedListener(this)
    }

    final override fun onCleared() {
        stateMachine.removeOnStateChangedListener(this)
    }

    final override fun onStateChanged(state: State) {
        computeViewState(state)?.let { (viewStateLiveData as MutableLiveData).value = it }
    }

    abstract fun computeViewState(state: State): VIEW_STATE?
}