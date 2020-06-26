package com.assignment.self.view.activities

import androidx.lifecycle.LifecycleOwner
import com.assignment.self.data.DataManager
import com.assignment.self.view.base.BaseViewModel
import sslwireless.android.easy.loyal.merchant.viewmodel.util.ApiCallbackHelper
import javax.inject.Inject


class MovieViewModel @Inject constructor(dataManager: DataManager) : BaseViewModel() {
    var dataManager = dataManager

    fun getMoviesPosters(lifecycleOwner: LifecycleOwner) {
        dataManager.apiHelper.getMoviesPosters(
            ApiCallbackHelper(
                livedata(lifecycleOwner,"MOVIES_POSTERS")
            )
        )
    }

    fun getMovieTrailers(id: Int,lifecycleOwner: LifecycleOwner) {
        dataManager.apiHelper.getMovieTrailers(
            id,
            ApiCallbackHelper(
                livedata(lifecycleOwner,"MOVIES_TRAILER")
            )
        )
    }

}

