package com.example.liyayu.myapplication.demoViews.easy_permissions_demo

import android.Manifest
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseApplication
import kotlinx.android.synthetic.main.fragment_easypermission_main.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * <pre>
 *     author : JX
 *     e-mail : liyayu@ffde.com
 *     time   : 2019/08/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */

class EasyPermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val TAG = "MainFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // Create view
        val v = inflater.inflate(R.layout.fragment_easypermission_main, container)

        // Button click listener
        v.button_sms.setOnClickListener { smsTask() }

        return v
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RC_SMS_PERM)
    fun smsTask() {
        if (EasyPermissions.hasPermissions(BaseApplication.instance, Manifest.permission.READ_SMS)) {
            // Have permission, do the thing!
            Toast.makeText(activity, "TODO: SMS things", Toast.LENGTH_LONG).show()
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_sms),
                    RC_SMS_PERM, Manifest.permission.READ_SMS)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, @NonNull perms: List<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, @NonNull perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)
    }
}
