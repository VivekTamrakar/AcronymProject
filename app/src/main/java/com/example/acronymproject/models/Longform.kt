package com.example.acronymproject.models

import com.example.acronymproject.models.Variation
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class Longform : Variation() {
    @SerializedName("vars")
    var otherVariations: ArrayList<Variation>? = null
    override fun toString(): String {
        return "Longform{" +
                "otherVariations=" + otherVariations +
                '}'
    }
}