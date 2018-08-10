package com.mboudraa.thescore.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.mboudraa.thescore.R
import com.mboudraa.thescore.core.FileTeamRepository
import com.mboudraa.thescore.core.IdleState
import com.mboudraa.thescore.core.State
import com.mboudraa.thescore.core.StateMachine


class MainActivity : AppCompatActivity() {

    private val viewModel by lazyViewModel { MainViewModel(StateMachine(IdleState(FileTeamRepository(Gson(), this)))) }

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        StateNavController(Navigation.findNavController(this, R.id.nav_host_fragment))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel.stateLiveData.observe(this, Observer { state ->
            Log.i("NBA_TEAM_VIEWER", "Current STATE --> $state")
            navController.navigate(state.toNavDirections())
            (state as? IdleState)?.loadTeams(viewModel.stateMachine)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(viewModel.stateMachine)
    }

    override fun onBackPressed() {
        if (!onSupportNavigateUp()) super.onBackPressed()
    }

}

class MainViewModel(val stateMachine: StateMachine) : ViewModel(), StateMachine.OnStateChangedListener {
    val stateLiveData: LiveData<State> = MutableLiveData()

    init {
        stateMachine.addOnStateChangedListener(this)
    }

    final override fun onCleared() {
        stateMachine.removeOnStateChangedListener(this)
    }

    override fun onStateChanged(state: State) {
        (stateLiveData as MutableLiveData).value = state
    }
}