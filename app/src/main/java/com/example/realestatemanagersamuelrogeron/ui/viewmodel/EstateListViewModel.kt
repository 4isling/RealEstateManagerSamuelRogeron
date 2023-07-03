package com.example.realestatemanagersamuelrogeron.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.SortType
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.data.state.ListViewState
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateListUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstatePicturesUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class EstatesListViewModel @Inject constructor(
    private val getEstateListUseCaseImpl: GetEstateListUseCaseImpl,
    private val getEstatePicturesUseCaseImpl: GetEstatePicturesUseCaseImpl
) : ViewModel() {

    private val TAG = "EstatesListViewModel:"
    private val _viewState = MutableStateFlow<ListViewState>(ListViewState.Loading)
    val viewState: StateFlow<ListViewState> =
        _viewState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() =
        _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing.asStateFlow()

    private val _canLoadMoreItems = MutableStateFlow(true)
    val canLoadMoreItems: StateFlow<Boolean> = _canLoadMoreItems.asStateFlow()
    private val _sortType = MutableStateFlow(SortType.Default)
    val sortType: MutableStateFlow<SortType> get() = _sortType

    init {
        Log.i(TAG, "init")
        loadEstates()
    }


    fun onSortTypeValueChange(newValue: SortType) {
        _sortType.value = newValue
        viewModelScope.launch {
            try {
                getEstateListUseCaseImpl.invoke(_sortType.value)
                    .catch { exception ->
                        _viewState.emit(ListViewState.Error(exception.message ?: "UnknownError"))
                        _isLoading.emit(false)
                    }.collectLatest { result ->
                        handleEstateResult(result)
                        _canLoadMoreItems.emit(result.isNotEmpty())
                    }
            }catch (e: Error){
                Log.e(TAG, "onSortTypeValueChange: error: $e", )
            }
        }
    }

    private fun loadEstates() = viewModelScope.launch {
        Log.i(TAG, "loadEstates: ")
            getEstateListUseCaseImpl.invoke(sortType.value)
            .catch { exception ->
                Log.e(TAG, "loadEstates: catch exception: $exception" )
                _viewState.emit(ListViewState.Error(exception.message ?: "UnknownError"))
                _isLoading.emit(false)
            }.collectLatest { result ->
                Log.i(TAG, "loadEstates: collect: result: $result")
                handleEstateResult(result)
                _canLoadMoreItems.emit(result.isNotEmpty())
            }
    }

    fun loadMoreItems() = viewModelScope.launch {
        if (_isLoading.value || !_canLoadMoreItems.value) return@launch
        _isLoading.emit(true)
        loadEstates()
    }


    private suspend fun handleEstateResult(result: List<Estate>) {
        Log.i(TAG, "handleEstateResult: ")
        val currentViewState = _viewState.value
        if (currentViewState is ListViewState.Success) {
            _viewState.emit(currentViewState.copy(estates = currentViewState.estates + result))
        } else {
            _viewState.emit(ListViewState.Success(result))
        }
        _isLoading.emit(false)
    }
}