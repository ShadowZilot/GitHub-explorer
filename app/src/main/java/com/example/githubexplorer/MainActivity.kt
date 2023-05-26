package com.example.githubexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.githubexplorer.common.RecreationActivity
import com.example.githubexplorer.common.navigateWithoutBack

class MainActivity : AppCompatActivity(), RecreationActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun relaunch() {
        recreate()
    }
}