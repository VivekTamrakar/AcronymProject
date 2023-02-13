package com.example.acronymproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.acronymproject.repositories.AcronymRepository
import androidx.lifecycle.LiveData
import com.example.acronymproject.api.APIResponse
import com.example.acronymproject.models.Acronym

class AcronymViewModel(application: Application) : AndroidViewModel(application) {
    private val acronymRepository: AcronymRepository
    val acronymListLiveData: LiveData<APIResponse<List<Acronym>?>?>

    init {
        println("ViewModel Created!")
        acronymRepository = AcronymRepository()
        acronymListLiveData = acronymRepository.apiResponseMutableLiveData
    }

    fun searchAcronyms(queryWord: String?) {
        acronymRepository.searchAcronyms(queryWord)
    }
}