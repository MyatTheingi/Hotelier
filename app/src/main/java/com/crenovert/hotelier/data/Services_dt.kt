package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Services_dt  (
        var checkBox: Boolean = false,
        var id: Int = 0,
        var name: String = "",
        var type: String = "",
        var price: Int = 0
)