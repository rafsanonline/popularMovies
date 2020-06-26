package com.assignment.self.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.assignment.self.R
import com.assignment.self.data.model.MovieData.movie_data
import com.assignment.self.data.model.MoviePosterResponses
import com.assignment.self.databinding.ActivityPopMoviesBinding
import com.assignment.self.util.LiveDataResult
import com.assignment.self.view.adapter.IAdapterListener
import com.assignment.self.view.base.BaseActivity
import com.assignment.self.view.base.BaseRecyclerAdapter
import com.assignment.self.view.base.BaseViewHolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*

class PopMovies : BaseActivity() {

    //variables
    lateinit var viewModel: MovieViewModel
    lateinit var binding: ActivityPopMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pop_movies)

    }

    // Yeah we are done with the popular movies API

    override fun viewRelatedTask() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java)
        showProgressDialog("Loading Movies")
        viewModel.getMoviesPosters(this)
    }

    override fun onSuccess(result: LiveDataResult<Response<ResponseBody>>, key: String) {
        hideProgressDialog()
        if (result.data!!.code() == 200) {
            val type = object : TypeToken<MoviePosterResponses>() {}.type
            val responses = Gson().fromJson<MoviePosterResponses>(
                result.data.body()!!.string(),
                type
            )
            movie_data = responses
            initiateView(responses.results)
        } else {
            Log.i("ERROR_MOVIE_POSTER", result.data.message())
        }
    }

    private fun initiateView(results: List<MoviePosterResponses.Result>) {
        binding.rvMoviePoster.layoutManager = GridLayoutManager(this, 2)
        binding.rvMoviePoster.adapter = BaseRecyclerAdapter(this, object : IAdapterListener {
            override fun <T> clickListener(position: Int, model: T, view: View) {
                model as MoviePosterResponses.Result
                val intent = Intent(this@PopMovies, MovieDetailActivity::class.java)
                intent.putExtra("movie_id", model.id)
                intent.putExtra("position", position)
                startActivity(intent)
            }

            override fun getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
                return MoviePosterViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.movie_poster_item,
                        parent, false
                    ), this@PopMovies
                )
            }
        }, results as ArrayList<MoviePosterResponses.Result>)
    }

    override fun onError(err: Throwable) {
        super.onError(err)
        hideProgressDialog()
        Toast.makeText(this,"Internet Connection Error", Toast.LENGTH_SHORT).show()
    }
}