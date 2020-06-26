package com.assignment.self.view.activities

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.assignment.self.BuildConfig.THUMB_URL
import com.assignment.self.data.model.MoviePosterResponses
import com.assignment.self.databinding.MoviePosterItemBinding
import com.assignment.self.view.adapter.IAdapterListener
import com.assignment.self.view.base.BaseViewHolder
import com.bumptech.glide.Glide

class MoviePosterViewHolder(itemView: ViewDataBinding, context : Context) : BaseViewHolder(itemView.root){

    var binding = itemView as MoviePosterItemBinding
    var mContext :Context = context

    override fun <T> onBind(position: Int, model: T, mCallback: IAdapterListener) {
        model as MoviePosterResponses.Result
        Glide.with(mContext).load(THUMB_URL+model.posterPath).into(binding.poster)

        binding.root.setOnClickListener {
            mCallback.clickListener(position,model,binding.root)
        }
    }
}
// Its working