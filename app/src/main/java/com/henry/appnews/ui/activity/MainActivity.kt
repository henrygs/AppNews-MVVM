package com.henry.appnews.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.henry.appnews.R
import com.henry.appnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{_, destination, _ ->
            title = destination.label
        }

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener {  }
        }
    }

}