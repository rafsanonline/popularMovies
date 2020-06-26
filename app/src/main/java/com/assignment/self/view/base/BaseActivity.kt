package com.assignment.self.view.base

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.assignment.self.R
import com.assignment.self.data.DataManager
import com.assignment.self.util.IObserverCallBack
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), IObserverCallBack {

    private lateinit var dialogs: ProgressDialog

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        dialogs = ProgressDialog(this)
        viewRelatedTask()
    }

    abstract fun viewRelatedTask()

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back)

    }


    fun hideKeyboard() {

        val inputManager = this.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        val focusedView = this.getCurrentFocus()

        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(
                focusedView!!.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS
            )
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
        if (!this.isFinishing && dialogs.isShowing) {
            dialogs.dismiss()
        }
    }

    override fun onLoading(isLoader: Boolean) {
        if (isLoader) {
            showProgressDialog("Please wait")
        } else {
            hideProgressDialog()
        }

    }

    fun hasPermissions(context: Context?, vararg permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            Log.e("log per", "granted 1")
            for (permission in permissions[0]) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.e("log per", "granted 2")
                    return false
                }
            }
        }
        return true
    }

    override fun onError(err: Throwable) {

    }

    fun addFragment(isReplace: Boolean, container: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (isReplace) {
            transaction.replace(container, fragment)
        } else {
            transaction.add(container, fragment)
        }
        transaction.commit()
    }

    fun showDialog(isCancelAble: Boolean, dialogFragment: BaseDialogFragment) {
        dialogFragment.isCancelable = isCancelAble
        dialogFragment.show(getSupportFragmentManager().beginTransaction(), "dialog")
    }
}