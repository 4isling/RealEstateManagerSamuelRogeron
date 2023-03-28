package com.example.realestatemanagersamuelrogeron.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EstateDetailViewModel @Inject constructor(private val estateRepository: EstateRepository): ViewModel() {
    private val _estate = MutableLiveData<Estate> ()

    fun getEstateDetail(){

    }
}