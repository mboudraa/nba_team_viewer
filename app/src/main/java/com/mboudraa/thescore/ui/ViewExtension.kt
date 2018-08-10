package com.mboudraa.thescore.ui

import android.view.View
import android.view.ViewGroup

fun <T : View> ViewGroup.bindView(id: Int) = bindView<T>(id, {})
inline fun <T : View> ViewGroup.bindView(id: Int, crossinline f: T.() -> Unit) = lazy(LazyThreadSafetyMode.NONE) { (findViewById(id) as T).apply(f) }