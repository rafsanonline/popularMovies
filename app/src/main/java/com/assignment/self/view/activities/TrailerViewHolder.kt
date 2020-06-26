package com.assignment.self.view.activities

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.assignment.self.data.model.MovieTrailerResponses
import com.assignment.self.databinding.MovieTrailerListBinding
import com.assignment.self.view.adapter.IAdapterListener
import com.assignment.self.view.base.BaseViewHolder

class TrailerViewHolder(itemView: ViewDataBinding, context: Context) :
    BaseViewHolder(itemView.root) {

    var binding = itemView as MovieTrailerListBinding
    var mContext: Context = context

    override fun <T> onBind(position: Int, model: T, mCallback: IAdapterListener) {
        model as MovieTrailerResponses.Result
        binding.trailerText.text = "Trailer ${position + 1}"
        binding.root.setOnClickListener {
            mCallback.clickListener(position, model, binding.root)
        }
    }
}
