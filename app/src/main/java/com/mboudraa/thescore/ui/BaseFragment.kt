package com.mboudraa.thescore.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mboudraa.thescore.core.StateMachine

abstract class BaseFragment<VM : ViewModel>(viewModelFactory: ViewModelFactory<VM>, viewModelClass: Class<VM>) : Fragment() {


    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val stateMachine = ViewModelProviders.of(activity!!).get(MainViewModel::class.java).stateMachine
                return viewModelFactory.createViewModel(stateMachine) as T
            }
        }).get(viewModelClass)
    }

    interface ViewModelFactory<VM : ViewModel> {
        fun createViewModel(stateMachine: StateMachine): VM
    }

    fun setSupportActionBar(toolbar: Toolbar?) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }
}