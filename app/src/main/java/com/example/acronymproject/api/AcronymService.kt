package com.example.acronymproject.api

import retrofit2.http.GET
import com.example.acronymproject.models.Acronym
import retrofit2.Call
import retrofit2.http.Query

interface AcronymService {
    @GET("software/acromine/dictionary.py")
    fun searchAcronyms(@Query("sf") queryWord: String?): Call<List<Acronym>>
}