package com.assignment.self.view.base

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.ViewModelProvider
import com.assignment.self.data.DataManager
import com.assignment.self.util.IObserverCallBack
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment(), IObserverCallBack {


    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialogs: ProgressDialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogs =  ProgressDialog(activity!!)
        viewRelatedTask()
    }

    abstract fun viewRelatedTask()


    fun showDialog(isCancelAble: Boolean, dialogFragment: BaseDialogFragment) {
        (activity as BaseActivity).showDialog(isCancelAble, dialogFragment)
    }

    override fun onLoading(isLoader: Boolean) {
        if (isLoader) {
            showProgressDialog("Please wait")
        } else {
            hideProgressDialog()
        }

    }

    fun showProgressDialog(message: String) {
        if (TextUtils.isEmpty(message)) {
            dialogs.setMessage("")
        } else {
            dialogs.setMessage(message)
        }
        if (!dialogs.isShowing) {
            dialogs.setCancelable(false)
            dialogs.show()
        }
    }

    fun hideProgressDialog() {
        if (!activity!!.isFinishing && dialogs.isShowing) {
            dialogs.dismiss()
        }
    }

    override fun onError(err: Throwable) {

    }


}