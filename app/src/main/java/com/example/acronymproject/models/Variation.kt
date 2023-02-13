package com.example.acronymproject.models

import com.google.gson.annotations.SerializedName

open class Variation {
    @SerializedName("lf")
    var longForm: String? = null

    @SerializedName("freq")
    var frequency: Int? = null

    @SerializedName("since")
    var since: Int? = null
    override fun toString(): String {
        return "Variation{" +
                "longForm='" + longForm + '\'' +
                ", frequency=" + frequency +
                ", since='" + since + '\'' +
                '}'
    }
}