package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Manage_Staff_dt (
        var checkBox: Boolean = false,
        var id: Int = 0,
        var name: String = "",
        var nrc: String = "",
        var email: String = "",
        var ph: String ="",
        var address: String = "",
        var password: String = "",
        var role: String = ""
)