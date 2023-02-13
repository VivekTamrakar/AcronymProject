package com.example.acronymproject.models

import com.google.gson.annotations.SerializedName
import com.example.acronymproject.models.Longform
import java.util.ArrayList

class Acronym {
    @SerializedName("sf")
    var shortForm: String? = null

    @SerializedName("lfs")
    var longForms: ArrayList<Longform>? = null
    override fun toString(): String {
        return "Acronym{" +
                "shortForm='" + shortForm + '\'' +
                ", longForms=" + longForms +
                '}'
    }
}