package com.eateasily.codewars.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eateasily.codewars.network.Resource
import com.eateasily.codewars.repository.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository
) : ViewModel() {

    private val _homeResponse = MutableStateFlow<Resource<Any>>(Resource.Empty)
    val getHomeDataResponse: StateFlow<Resource<Any>> = _homeResponse


    fun homeDataRequest() = viewModelScope.launch {
        try {
            _homeResponse.value = Resource.Loading
            _homeResponse.value = userListRepository.getHomeResponse()
        } catch (ex: Exception){
            ex.printStackTrace()
        }
    }


}