package com.mboudraa.thescore.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mboudraa.thescore.R
import com.mboudraa.thescore.core.StateMachine
import com.mboudraa.thescore.ui.BaseFragment

class TeamDetailFragment : BaseFragment<TeamViewModel>(Factory, TeamViewModel::class.java) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.team_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).apply {
            setSupportActionBar((view as TeamView).toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.viewStateLiveData.observe(this, Observer((view as TeamView)::render))
    }

    companion object Factory : ViewModelFactory<TeamViewModel> {
        override fun createViewModel(stateMachine: StateMachine) = TeamViewModel(stateMachine)
    }
}


