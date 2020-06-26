package com.assignment.self.view.adapter

import android.view.View
import android.view.ViewGroup
import com.assignment.self.view.base.BaseViewHolder

interface IAdapterListener {
    fun <T> clickListener(position: Int, model: T, view: View)
    fun  getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder
}