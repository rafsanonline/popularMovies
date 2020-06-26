package com.assignment.self.view

import android.content.Context
import androidx.databinding.ViewDataBinding

import com.assignment.self.view.adapter.IAdapterListener
import com.assignment.self.view.base.BaseViewHolder

class EmptyViewHolder (itemView: ViewDataBinding, context: Context) :
    BaseViewHolder(itemView.root) {
   // var binding = itemView as EmptyPageBinding

    override fun <T> onBind(position: Int, itemModel: T, listener: IAdapterListener) {

    }
}