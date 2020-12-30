package tech.jayamakmurdigital.spamdetector.utils

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionManager(fragment: Fragment) {
    val requestPermissionLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

    }

    fun getPermission() {
        requestPermissionLauncher.launch(Manifest.permission.READ_SMS)
    }
}