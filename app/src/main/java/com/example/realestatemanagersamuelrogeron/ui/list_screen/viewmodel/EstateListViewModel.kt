package com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllEstatesWithPicturesUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstatesListViewModel @Inject constructor(
    private val getAllEstatesWithPicturesUseCaseImpl: GetAllEstatesWithPicturesUseCaseImpl
) : ViewModel() {

    private val TAG = "EstatesListViewModel:"
    private val _viewState = MutableStateFlow<ListViewState>(ListViewState.Loading)
    val viewState: StateFlow<ListViewState> = _viewState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _canLoadMoreItems = MutableStateFlow(true)
    val canLoadMoreItems: StateFlow<Boolean> = _canLoadMoreItems.asStateFlow()

    private val _filter = MutableStateFlow(EstateFilter())
    val filter: StateFlow<EstateFilter> = _filter.asStateFlow()

    init {
        Log.i(TAG, "init")
        loadEstatesWithDetail()
    }

    private fun loadEstatesWithDetail() {
        viewModelScope.launch {
            _isLoading.emit(true)
            getAllEstatesWithPicturesUseCaseImpl.execute(_filter.value)
                .catch { exception ->
                    _viewState.emit(ListViewState.Error(exception.message ?: "UnknownError"))
                    _isLoading.emit(false)
                }
                .collectLatest { estatesWithDetail ->
                    _viewState.emit(ListViewState.Success(estatesWithDetail, _filter.value))
                    _isLoading.emit(false)
                }
        }
    }

    fun updateFilter(newFilter: EstateFilter) {
        _filter.value = newFilter
        loadEstatesWithDetail()
    }
}
