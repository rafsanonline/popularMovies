package com.assignment.self.view.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.assignment.self.data.DataManager
import com.assignment.self.util.IObserverCallBack
import javax.inject.Inject

abstract class BaseDialogFragment : DialogFragment(), IObserverCallBack {

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewRelatedTask()
    }


    abstract fun viewRelatedTask()

}