package com.crenovert.hotelier

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.databinding.ActivityMainBinding
import com.crenovert.hotelier.main_fragment.*
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        /* val message = intent.getStringExtra("message")

         when(message)
         {
             "Administrator" ->  resetNavDrawer()
             "Manager" -> {
                 nav_view.menu.findItem(R.id.navRC).isVisible = false
                 nav_view.menu.findItem(R.id.nav_AL).isVisible = false
             }
             "Receptionist" ->
             {
                 nav_view.menu.findItem(R.id.navML_one).isVisible = false
                 nav_view.menu.findItem(R.id.navML_two).isVisible = false
                 nav_view.menu.findItem(R.id.nav_AL).isVisible = false
             }
         }
         resetNavDrawer()*/


        binding.navView.setNavigationItemSelectedListener(this)


        //home fragment is added to frame layout at content_main.xml
        if (binding.appBarMain != null) {
            val fragment: Fragment = DashboardFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment).commit()
        }


        goTONavigation_Next(R.id.nav_reports, R.menu.reports_menu, "Reports")

    }

    private fun resetNavDrawer() {
        binding.navView.menu.findItem(R.id.navML_one).isVisible = true
        binding.navView.menu.findItem(R.id.navML_two).isVisible = true
        binding.navView.menu.findItem(R.id.navRC).isVisible = true
        binding.navView.menu.findItem(R.id.nav_AL).isVisible = true
    }

    private fun goTONavigation_Next(menuItem: Int, menu_layout: Int, str: String) {

        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()

        val imageView = binding.navView.menu.findItem(menuItem).actionView as? ImageView
        imageView?.setOnClickListener {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(menu_layout)

            var menuitem = binding.navView.menu.getItem(0)
            menuitem.setIcon(R.drawable.ic_navigate_before)


        }

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)



        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_logout -> return true
            R.id.action_help_feedback -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var newFragment: Fragment


        when (item.itemId) {
            R.id.nav_dashboard -> {


                Toast.makeText(applicationContext, "Dashboard", Toast.LENGTH_SHORT).show()
                newFragment = DashboardFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.nav_guests -> {

                Toast.makeText(applicationContext, "Guests", Toast.LENGTH_SHORT).show()
                newFragment = GuestsFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)
                }
                transaction.commit()

            }

            R.id.nav_room_types -> {


                Toast.makeText(applicationContext, "Room Types", Toast.LENGTH_SHORT).show()
                newFragment = RoomTypesFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.nav_rooms -> {
                Toast.makeText(applicationContext, "Rooms", Toast.LENGTH_SHORT).show()
                newFragment = RoomsFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.nav_bed_types -> {

                Toast.makeText(applicationContext, "Rooms", Toast.LENGTH_SHORT).show()
                newFragment = BedTypesFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.nav_calendar -> {


                Toast.makeText(applicationContext, "Calendar", Toast.LENGTH_SHORT).show()
                newFragment = CalendarFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()

            }
            R.id.nav_availablity -> {


                Toast.makeText(applicationContext, "Availability", Toast.LENGTH_SHORT).show()
                newFragment = AvailabilityFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()

            }
            R.id.nav_view_assign_calendar -> {


                Toast.makeText(applicationContext, "Assign Calendar", Toast.LENGTH_SHORT).show()
                newFragment = AssignCalendarFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()

            }


            R.id.nav_walkin_reservations -> {


                Toast.makeText(applicationContext, "Reservations", Toast.LENGTH_SHORT).show()
                newFragment = WalkinReservationsFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()

            }

            R.id.nav_services -> {
                Toast.makeText(applicationContext, "Services", Toast.LENGTH_SHORT).show()
                newFragment = ServicesFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()


            }

            R.id.nav_permissions -> {


                Toast.makeText(applicationContext, "Permissions", Toast.LENGTH_SHORT).show()
                newFragment = PermissionsFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()

            }
            R.id.nav_manage_staff -> {


                Toast.makeText(applicationContext, "Manage Staff", Toast.LENGTH_SHORT).show()
                newFragment = ManageStaffFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.nav_reports -> {
                binding.navView.menu.clear()
                binding.navView.inflateMenu(R.menu.reports_menu)

                var menuitem = binding.navView.menu.getItem(0)
                menuitem.setIcon(R.drawable.ic_navigate_before)

                newFragment = ReportOccupancyFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }

            R.id.menu_reports -> {
                binding.navView.menu.clear()
                binding.navView.inflateMenu(R.menu.activity_main_drawer)

                newFragment = DashboardFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()

            }
            R.id.menu_daily_occupancy -> {
                Toast.makeText(applicationContext, "Daily Occupancy", Toast.LENGTH_SHORT).show()
                newFragment = ReportOccupancyFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.menu_daily_reports -> {
                Toast.makeText(applicationContext, "Daily Reports", Toast.LENGTH_SHORT).show()
                newFragment = ReportDailyFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.menu_monthly_reports -> {
                Toast.makeText(applicationContext, "Monthly Reports", Toast.LENGTH_SHORT).show()
                newFragment = ReportMonthlyFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()

            }
            R.id.menu_yearly_reports -> {
                Toast.makeText(applicationContext, "Yearly Reports", Toast.LENGTH_SHORT).show()
                newFragment = ReportYearlyFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.menu_guest_reports -> {
                Toast.makeText(applicationContext, "Guest Reports", Toast.LENGTH_SHORT).show()
                newFragment = ReportGuestFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }

            R.id.nav_change_password -> {


                Toast.makeText(applicationContext, "Change Password", Toast.LENGTH_SHORT).show()
                newFragment = ChangePasswordFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()

            }
            R.id.nav_switch_user -> {


                Toast.makeText(applicationContext, "Switch User", Toast.LENGTH_SHORT).show()
                newFragment = SwitchUserFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
            R.id.nav_general -> {


                Toast.makeText(applicationContext, "General Setting", Toast.LENGTH_SHORT).show()
                newFragment = GeneralFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }

            R.id.nav_help_feedback -> {


                Toast.makeText(applicationContext, "Help and Feedback", Toast.LENGTH_SHORT).show()
                newFragment = HelpFeedbackFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, newFragment)

                }
                transaction.commit()
            }
        }

        when (item.itemId) {
            R.id.nav_reports -> {
                Toast.makeText(applicationContext, "Next", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_reports -> {
                Toast.makeText(applicationContext, "Previous", Toast.LENGTH_SHORT).show()
            }
            else -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        return true
    }
}
