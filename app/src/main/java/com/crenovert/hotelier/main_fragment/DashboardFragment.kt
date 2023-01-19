package com.crenovert.hotelier.main_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.AddActivity
import com.crenovert.hotelier.MainActivity
import com.crenovert.hotelier.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var intent_one = Intent(activity?.applicationContext, MainActivity::class.java)
        binding.imgGuests.setOnClickListener {
            intent_one.putExtra("message", "guest")
            activity?.startActivity(intent_one)
        }
        binding.imgRoomTypes.setOnClickListener {
            intent_one.putExtra("message", "room_type")
            activity?.startActivity(intent_one)
        }
        binding.imgRooms.setOnClickListener {
            intent_one.putExtra("message", "room")
            activity?.startActivity(intent_one)
        }
        binding.imgWalkinReservations.setOnClickListener {
            intent_one.putExtra("message", "walkin_reservation")
            activity?.startActivity(intent_one)
        }


        var intent = Intent(activity?.applicationContext, AddActivity::class.java)

        binding.txtAddGuest.setOnClickListener {
            intent.putExtra("message", "guest")
            activity?.startActivity(intent)
        }
        binding.txtAddRoomType.setOnClickListener {
            intent.putExtra("message", "room_type")
            activity?.startActivity(intent)
        }
        binding.txtAddRoom.setOnClickListener {
            intent.putExtra("message", "room")
            activity?.startActivity(intent)
        }
        binding.txtAddWalkinReservation.setOnClickListener {
            intent.putExtra("message", "walkin_reservation")
            activity?.startActivity(intent)
        }
    }

}
