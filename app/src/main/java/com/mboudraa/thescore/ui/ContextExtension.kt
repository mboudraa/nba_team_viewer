package com.mboudraa.thescore.ui

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlin.LazyThreadSafetyMode.NONE

inline fun <reified VM : ViewModel> FragmentActivity.lazyViewModel(crossinline factory: () -> VM) = lazy(NONE) {
    ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =factory() as T
    }).get(VM::class.java)
}