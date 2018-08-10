package com.mboudraa.thescore.ui.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.mboudraa.thescore.R
import com.mboudraa.thescore.core.Sort
import com.mboudraa.thescore.core.StateMachine
import com.mboudraa.thescore.ui.BaseFragment

class TeamListFragment : BaseFragment<TeamListViewModel>(Factory, TeamListViewModel::class.java) {


    private var sortDialog: AlertDialog? = null
    private val contentView: TeamListView
        get() = view as TeamListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.team_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSupportActionBar(contentView.toolbar)
        contentView.setOnTeamSelectedListener(viewModel::selectTeam)
    }

    override fun onStart() {
        super.onStart()
        viewModel.viewStateLiveData.observe(this, Observer(contentView::render))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.teams_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.teams_sort -> viewModel.viewStateLiveData.value?.let { showSortDialog(it.sort) }
                    ?: false
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSortDialog(sort: Sort): Boolean {
        sortDialog?.dismiss()
        sortDialog = AlertDialog.Builder(activity!!)
                .setSingleChoiceItems(activity!!.resources.getStringArray(R.array.sort), sort.ordinal) { dialog, which ->
                    viewModel.sort(Sort.values()[which])
                    dialog.dismiss()
                }
                .show()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        sortDialog?.dismiss()
    }

    companion object Factory : ViewModelFactory<TeamListViewModel> {
        override fun createViewModel(stateMachine: StateMachine) = TeamListViewModel(stateMachine)
    }

}

