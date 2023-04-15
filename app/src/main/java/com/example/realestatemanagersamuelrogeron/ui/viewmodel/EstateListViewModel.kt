package com.example.realestatemanagersamuelrogeron.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstatesListViewModel @Inject constructor(
    private val estateRepository: EstateRepository
) : ViewModel() {

    private val _estates = MutableLiveData<List<Estate>>()

    val estate: MutableLiveData<List<Estate>> get() = _estates

    fun getAllEstates(): MutableLiveData<List<Estate>> {
        val result = MutableLiveData<List<Estate>>()
        viewModelScope.launch {
            estateRepository.getAllEstate().collect() {
                _estates.value = it
            }
        }
        return result
    }


}