package com.assignment.self.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.assignment.self.BuildConfig.THUMB_URL
import com.assignment.self.R
import com.assignment.self.data.model.MovieData.movie_data
import com.assignment.self.data.model.MovieTrailerResponses
import com.assignment.self.databinding.ActivityMovieDetailBinding
import com.assignment.self.util.LiveDataResult
import com.assignment.self.view.adapter.IAdapterListener
import com.assignment.self.view.base.BaseActivity
import com.assignment.self.view.base.BaseRecyclerAdapter
import com.assignment.self.view.base.BaseViewHolder
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*


class MovieDetailActivity : BaseActivity() {

    var movieId : Int?=null
    var position : Int?=null
    lateinit var viewModel: MovieViewModel
    lateinit var binding : ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    override fun viewRelatedTask() {
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MovieViewModel::class.java)
        movieId = intent.getIntExtra("movie_id",0)
        position = intent.getIntExtra("position",0)
        setData()
        showProgressDialog("Loading Trailers")
        viewModel.getMovieTrailers(movie_data.results.get(position!!).id,this)

    }

    private fun setData() {
        val movie_results = movie_data.results.get(position!!)
        binding.movieTitle.text = movie_results.title
        Glide.with(this).load(THUMB_URL+movie_results.posterPath).into(binding.movieImage)
        binding.movieYear.text = movie_results.releaseDate.split("-")[0]
        binding.movieRating.text = movie_results.voteAverage.toString() +"/10"
        binding.movieDetailText.text = movie_results.overview
    }

    override fun onSuccess(result: LiveDataResult<Response<ResponseBody>>, key: String) {
        if (result.data!!.code() == 200) {
            val type = object : TypeToken<MovieTrailerResponses>() {}.type
            val responses = Gson().fromJson<MovieTrailerResponses>(
                result.data.body()!!.string(),
                type
            )
            hideProgressDialog()
            initiateView(responses.results)
        }
        else {
            Toast.makeText(this,result.data.message(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initiateView(results: List<MovieTrailerResponses.Result>) {
     binding.rvTrailerView.layoutManager = GridLayoutManager(this,1)
     binding.rvTrailerView.adapter = BaseRecyclerAdapter(this, object : IAdapterListener{
         override fun <T> clickListener(position: Int, model: T, view: View) {
            model as MovieTrailerResponses.Result
            val intent = Intent(this@MovieDetailActivity,YoutubePlayerActivity::class.java)
            intent.putExtra("youtube_video_key",model.key)
             startActivity(intent)
         }

         override fun getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
           return TrailerViewHolder(
                   DataBindingUtil.inflate(
                       LayoutInflater.from(parent.context),
                       R.layout.movie_trailer_list,
                       parent, false
                   ), this@MovieDetailActivity
           )
         }
     },results as ArrayList<MovieTrailerResponses.Result>)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onError(err: Throwable) {
        super.onError(err)
        hideProgressDialog()
        Toast.makeText(this,"Internet Connection Error",Toast.LENGTH_SHORT).show()
    }
}