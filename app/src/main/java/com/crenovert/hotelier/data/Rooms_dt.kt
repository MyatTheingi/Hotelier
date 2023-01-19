package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Rooms_dt(
        var checkBox: Boolean = false,
        var id: Int = 0,
        var no: String = "",
        var type: String = "",
        var floor: String = "",
        var status: String = ""
)