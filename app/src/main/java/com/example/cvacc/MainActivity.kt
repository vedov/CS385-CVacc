package com.example.cvacc

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var data: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = intent.getStringExtra("text")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.appointmentFragment,
                R.id.statsFragment,
                R.id.notEligibleFragment,
                R.id.eligibleFragment
            ), drawerLayout
        )

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        NavigationUI.setupWithNavController(bottomNavView, navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            appBarConfiguration.topLevelDestinations.contains(destination.id)
            BottomNavController(destination)

            val toolbarTitle: TextView = toolbar.findViewById(R.id.toolbar_title)
            toolbarTitle.text = navController.currentDestination?.label.toString()
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)

        sendData()
    }

    private fun BottomNavController(destination: NavDestination) {
        when (destination.id) {
            R.id.nav_home -> showBottomNav()
            R.id.appointmentFragment -> showBottomNav()
            R.id.statsFragment -> showBottomNav()
            else -> {
                hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        val navBottomView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        navBottomView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        val navBottomView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        navBottomView.visibility = View.GONE

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun sendData(): String? {
        return data
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (!findNavController(R.id.nav_host_fragment).navigateUp()) {
            moveTaskToBack(false)
        }

    }

}