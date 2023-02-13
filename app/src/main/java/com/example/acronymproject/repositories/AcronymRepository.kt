package com.example.acronymproject.repositories

import com.example.acronymproject.api.APIResponse.Companion.forSuccess
import com.example.acronymproject.api.APIResponse.Companion.forErrorMessage
import com.example.acronymproject.api.AcronymService
import androidx.lifecycle.MutableLiveData
import com.example.acronymproject.api.APIResponse
import com.example.acronymproject.models.Acronym
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class AcronymRepository {
    private val acronymService: AcronymService
    val apiResponseMutableLiveData: MutableLiveData<APIResponse<List<Acronym>?>?>

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        acronymService = retrofit.create(AcronymService::class.java)
        apiResponseMutableLiveData = MutableLiveData(null)
    }

    fun searchAcronyms(queryWord: String?) {
        val call = acronymService.searchAcronyms(queryWord)

        call!!.enqueue(object : Callback<List<Acronym>> {
            override fun onResponse(
                call: Call<List<Acronym>>?,
                response: Response<List<Acronym>>?
            ) {
                if (response?.isSuccessful == true)
                    apiResponseMutableLiveData.postValue(forSuccess(response.body()))
                else apiResponseMutableLiveData.postValue(forErrorMessage(response))
            }

            override fun onFailure(call: Call<List<Acronym>>?, t: Throwable?) {
                call?.cancel()
                apiResponseMutableLiveData.postValue(forErrorMessage(t))
            }
        })
    }

    companion object {
        private const val BASE_URL = "http://www.nactem.ac.uk/"
    }
}