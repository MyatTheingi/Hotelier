package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Room_Types_dt(
        var checkBox: Boolean = false,
        var id: Int = 0,
        var name: String = "",
        var bed_type: String = "",
        var features: String = ""
)