package com.assignment.self.di
import com.assignment.self.view.activities.MovieDetailActivity
import com.assignment.self.view.activities.PopMovies
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun PopMovies() : PopMovies

    @ContributesAndroidInjector
    internal abstract fun MovieDetailActivity() : MovieDetailActivity
}