package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Assign_Calendar_dt (
        var checkBox :Boolean= false,
        var id: Int = 0,
        var name: String = "",
        var type: String = "",
        var from: String = "",
        var to: String = "",
        var price: Int = 0
)