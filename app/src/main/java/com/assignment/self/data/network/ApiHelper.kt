package com.assignment.self.data.network

import io.reactivex.Maybe
import com.assignment.self.data.network.api_call_factory.ApiGetCall
import com.assignment.self.data.network.api_call_factory.ApiPostCall
import com.assignment.self.data.network.api_call_factory.ApiPostCallWithDocument
import okhttp3.ResponseBody
import retrofit2.Response
import sslwireless.android.easy.loyal.merchant.viewmodel.util.ApiCallbackHelper

class ApiHelper(apiService: IApiService) {


    val apiService = apiService

    //call type
    val CALL_TYPE_GET = "get"
    val CALL_TYPE_POST = "post"
    val CALL_TYPE_POST_WITH_DOCUMENT = "post with document"

    fun getMoviesPosters(apiCallbackHelper: ApiCallbackHelper) {
        val hashMap = HashMap<String, Any>()
        hashMap.put("api_key","ff828a72b45f8a8bc8835e4999ee3f6a")
        getApiCallObservable(CALL_TYPE_GET,"popular",hashMap).subscribe(apiCallbackHelper)
    }

    fun getMovieTrailers(id: Int, apiCallbackHelper: ApiCallbackHelper) {
        val hashMap = HashMap<String, Any>()
        hashMap.put("api_key","ff828a72b45f8a8bc8835e4999ee3f6a")
        getApiCallObservable(CALL_TYPE_GET,"$id/videos",hashMap).subscribe(apiCallbackHelper)
    }


    fun <T> getApiCallObservable(callType: String, path: String, hashMap: HashMap<String, T>): Maybe<Response<ResponseBody>> {
        if (callType.equals(CALL_TYPE_GET)) {
            return ApiGetCall().getMaybeObserVable(apiService, path, hashMap!!)
        }
        else if (callType.equals(CALL_TYPE_POST)) {
            return ApiPostCall().getMaybeObserVable(apiService, path, hashMap!!)

        } else  {
            return ApiPostCallWithDocument().getMaybeObserVable(apiService, path, hashMap!!)

        }
    }
}