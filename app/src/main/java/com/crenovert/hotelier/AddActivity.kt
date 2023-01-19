package com.crenovert.hotelier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.add.*
import com.crenovert.hotelier.databinding.ActivityAddBinding


class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (binding.fragmentAddContainer != null) {
            val fragment: Fragment = AddBedTypeFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_add_container, fragment).commit()
        }


        val message = intent.getStringExtra("message")
        changeToAddFragment(message)


    }

    private fun changeToAddFragment(message: String?) {

        var newFragment: Fragment
        when (message) {


            "bed_type" -> {
                newFragment = AddBedTypeFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()

            }


            "calendar" -> {
                newFragment = AddCalendarFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()
            }
            "guest" -> {
                newFragment = AddGuestFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()
            }
            "manage_staff" -> {
                newFragment = AddManageStaffFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()
            }
            "permission" -> {
                newFragment = AddPermissionFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()
            }

            "room" -> {
                newFragment = AddRoomFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()
            }
            "room_type" -> {
                newFragment = AddRoomTypeFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()
            }
            "service" -> {
                newFragment = AddServiceFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()
            }
            "walkin_reservation" -> {
                newFragment = AddWalkinReservationFragment()
                val transaction = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_add_container, newFragment)

                }
                transaction.commit()
            }
        }
    }
}
