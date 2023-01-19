package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Bed_Types_dt(
        var checkBox: Boolean = false,
        var id: Int = 0,
        var name: String = "",
        var dimen: String = ""

)