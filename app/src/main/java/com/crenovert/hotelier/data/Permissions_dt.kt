package com.crenovert.hotelier.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Permissions_dt  (
        var checkBox: Boolean = false,
        var id: Int = 0,
        var name: String = "",
        var permission: ArrayList<String> = arrayListOf()

)