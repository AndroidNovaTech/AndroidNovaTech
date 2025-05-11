package com.zain.giftcard.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.zain.giftcard.R
import com.zain.giftcard.fregment.JokeCategoryFragment
import com.zain.giftcard.fregment.ScratchCardFragment


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        bottomNav = findViewById(R.id.bottom_nav)
        toolbar = findViewById(R.id.toolbar)

        // Set Toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Setup drawer toggle
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Default fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ScratchCardFragment())
            .commit()

        // Bottom navigation
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_scratch_card -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ScratchCardFragment())
                        .commit()
                    true
                }

                R.id.nav_joke_category -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, JokeCategoryFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
