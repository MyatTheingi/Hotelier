package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Walkin_Reservations_dt (
        var checkBox: Boolean = false,
        var id: Int = 0,
        var name: String = "",
        var nrc: String = "",
        var email: String = "",
        var ph: String ="",
        var address: String = "",
        var checkin: String = "",
        var checkout: String = "",
        var total: Int = 0,
        var paid: Int = 0,
        var balance: Int = 0,
        var bookingtime: String = "",
        var status: String = ""
)