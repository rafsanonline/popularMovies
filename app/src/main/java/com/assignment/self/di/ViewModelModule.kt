package com.assignment.self.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.self.view.activities.MovieViewModel

import com.assignment.self.view.base.BaseViewmodelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: BaseViewmodelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun postMovieViewModel(viewModel: MovieViewModel) : ViewModel
}