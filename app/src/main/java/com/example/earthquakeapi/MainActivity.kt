package com.example.earthquakeapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() , EarrthQuakeFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment, EarrthQuakeFragment.newInstance())
                .commit()
        }
    }

    override fun onEarthQuakeSelected(locationIntent: Intent) {
        if (locationIntent.resolveActivity(packageManager)!=null){
            startActivity(locationIntent)
        }
    }
}