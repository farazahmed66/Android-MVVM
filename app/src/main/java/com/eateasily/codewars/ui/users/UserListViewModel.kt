package com.eateasily.codewars.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eateasily.codewars.models.User
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

    private val _homeResponse = MutableStateFlow<Resource<User>>(Resource.Empty)
    val getHomeDataResponse: StateFlow<Resource<User>> = _homeResponse


    fun searchUser(query: String) = viewModelScope.launch {
            _homeResponse.value = Resource.Loading
            _homeResponse.value = userListRepository.searchUser(query)

    }
}