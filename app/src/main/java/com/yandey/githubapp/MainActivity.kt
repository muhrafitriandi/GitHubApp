package com.yandey.githubapp

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yandey.githubapp.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreenWithAnim(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.navHostFragment)
        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            when (navDestination.id) {
                R.id.detailFragment -> binding.bottomBar.visibility = GONE
                else -> binding.bottomBar.visibility = VISIBLE
            }
        }
        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        binding.bottomBar.setupWithNavController(menu!!, navController)
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

    private fun installSplashScreenWithAnim(savedInstanceState: Bundle?) {
        installSplashScreen().also {
            if (savedInstanceState == null) {
                it.setOnExitAnimationListener { splashScreenView ->
                    // Create custom animation.
                    splashScreenView.iconView.animate().rotation(180F).duration = 500L
                    ObjectAnimator.ofFloat(
                        splashScreenView.iconView,
                        View.TRANSLATION_Y,
                        0f,
                        -splashScreenView.iconView.height.toFloat()
                    ).apply {
                        interpolator = AnticipateInterpolator()
                        duration = 500L
                        doOnEnd { splashScreenView.remove() }
                        start()
                    }
                }
            }
        }
    }
}