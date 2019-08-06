package com.example.liyayu.myapplication.demoViews.easy_permissions_demo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.liyayu.myapplication.R
import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity
import kotlinx.android.synthetic.main.activity_easypermission_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
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
class EasyPermissionActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {


    private val TAG = "MainActivity"
    private val LOCATION_AND_CONTACTS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easypermission_main)

        // Button click listener that will request one permission.
        button_camera.setOnClickListener(View.OnClickListener { cameraTask() })

        // Button click listener that will request two permissions.
        button_location_and_contacts.setOnClickListener(View.OnClickListener { locationAndContactsTask() })
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
    }

    private fun hasLocationAndContactsPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, *LOCATION_AND_CONTACTS)
    }

    private fun hasSmsPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_SMS)
    }

    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    fun cameraTask() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_camera),
                    RC_CAMERA_PERM,
                    Manifest.permission.CAMERA)
        }
    }

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    fun locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            // Have permissions, do the thing!
            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location_contacts),
                    RC_LOCATION_CONTACTS_PERM,
                    *LOCATION_AND_CONTACTS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, @NonNull perms: List<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, @NonNull perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            val yes = getString(R.string.yes)
            val no = getString(R.string.no)

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            if (hasCameraPermission()) yes else no,
                            if (hasLocationAndContactsPermissions()) yes else no,
                            if (hasSmsPermission()) yes else no),
                    Toast.LENGTH_LONG)
                    .show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted:$requestCode")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied:$requestCode")
    }
}