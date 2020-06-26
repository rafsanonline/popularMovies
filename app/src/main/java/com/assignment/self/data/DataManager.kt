package com.assignment.self.data

import com.assignment.self.data.local_db.RoomHelper
import com.assignment.self.data.network.ApiHelper
import com.assignment.self.data.prefence.PreferencesHelper
import javax.inject.Inject

class DataManager @Inject constructor(
    preferencesHelper: PreferencesHelper,roomHelper: RoomHelper, apiHelper: ApiHelper
) : IDataManager {

    val mPref = preferencesHelper
    val roomHelper = roomHelper
    val apiHelper = apiHelper

}