package ru.grachev.market.core_utils.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.grachev.market.core_utils.model.NetworkState

abstract class BaseViewModel: ViewModel() {
    private val _networkState: MutableLiveData<NetworkState> = MutableLiveData()
    val networkState: LiveData<NetworkState> = _networkState

    fun observeNetworkState(networkStateFlow: Flow<NetworkState>) {
        viewModelScope.launch {
            networkStateFlow
                .flowOn(Dispatchers.IO)
                .onEach {
                    _networkState.value = it
                }
                ?.flowOn(Dispatchers.Main)
                ?.collect()
        }
    }
}