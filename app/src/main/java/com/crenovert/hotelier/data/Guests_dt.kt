package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data  class Guests_dt(
        var checkBox:Boolean = false,
        var id: Int = 0,
        var name: String = "",
        var nrc: String = "",
        var email: String = "",
        var ph: String = "",
        var address: String = "",
        var country: String = ""
)