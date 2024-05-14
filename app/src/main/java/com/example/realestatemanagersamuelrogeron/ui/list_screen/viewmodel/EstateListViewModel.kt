package com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithPictures
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllEstatesWithPicturesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.utils.SortType
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
    val viewState: StateFlow<ListViewState> =
        _viewState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() =
            _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing.asStateFlow()

    private val _canLoadMoreItems = MutableStateFlow(true)
    val canLoadMoreItems: StateFlow<Boolean> = _canLoadMoreItems.asStateFlow()
    private val _sortType = MutableStateFlow(SortType.Default)
    val sortType: MutableStateFlow<SortType> get() = _sortType


    private val _estateList = MutableStateFlow(emptyList<Estate>())
    private val _estatePic =
        MutableStateFlow(listOf(EstateMedia(uri = "", name = "")))


    private val _estatesWithPictures = MutableStateFlow<List<EstateWithPictures>>(emptyList())
    val estatesWithPictures: StateFlow<List<EstateWithPictures>> = _estatesWithPictures.asStateFlow()



    init {
        Log.i(TAG, "init")
        loadEstatesWithPictures()
    }


    fun onSortTypeValueChange(newValue: SortType) {
        _sortType.value = newValue
        viewModelScope.launch {
            try {
                getAllEstatesWithPicturesUseCaseImpl.execute(_sortType.value)
                    .catch { exception ->
                        _viewState.emit(ListViewState.Error(exception.message ?: "UnknownError"))
                        _isLoading.emit(false)
                    }.collectLatest { result ->
                        _estatesWithPictures.value = result
                        _canLoadMoreItems.emit(result.isNotEmpty())
                        _viewState.emit(ListViewState.Success(result))
                    }
            } catch (e: Error) {
                Log.e(TAG, "onSortTypeValueChange: error: $e")
            }
        }
    }

    private fun loadEstatesWithPictures() {
        viewModelScope.launch {
            getAllEstatesWithPicturesUseCaseImpl.execute(_sortType.value)
                .catch { exception ->
                    // Handle exceptions, if any
                    Log.w(TAG, "loadEstatesWithPictures: ", exception)
                }
                .collect { estatesWithPictures ->
                    _estatesWithPictures.value = estatesWithPictures
                    _viewState.emit(ListViewState.Success(estatesWithPictures))
                }
        }
    }
}