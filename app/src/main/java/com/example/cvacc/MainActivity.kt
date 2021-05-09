package com.example.cvacc



import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var mAuth: FirebaseAuth
    var databaseReference: DatabaseReference ?= null
    var database: FirebaseDatabase ?= null
    var niz : String ?= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        niz = intent.getStringExtra("text")

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)


        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.appointmentFragment, R.id.statsFragment
            ), drawerLayout
        )

        val bottom_nav_view : BottomNavigationView = findViewById(R.id.bottom_nav_view)
        NavigationUI.setupWithNavController(bottom_nav_view, navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevelDestination =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
                BottomNavController(destination)

            //Center Toolbar and set title
            val toolbar_title : TextView =  toolbar.findViewById(R.id.toolbar_title)
            toolbar_title.text = navController.currentDestination?.label.toString()
        }


        supportActionBar?.setDisplayShowTitleEnabled(false)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference?.child("profile")

        //loadProfile()

        sendData()
    }


    //Show Bottom Navigation depending on which fragment is open
    private fun BottomNavController(destination: NavDestination){
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    public fun sendData(): String? {
        return niz
    }

}