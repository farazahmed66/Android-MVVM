package com.eateasily.codewars.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eateasily.codewars.domain.Resource
import com.eateasily.codewars.domain.model.User
import com.eateasily.codewars.domain.usecase.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    private val _userListResponse = MutableStateFlow<Resource<User>>(Resource.Empty)
    val getUserDataResponse: StateFlow<Resource<User>> = _userListResponse

    var lastQuery: String = ""
        private set

    fun searchUser(query: String) {
        lastQuery = query
        viewModelScope.launch {
            searchUserUseCase(query)
                .collect { _userListResponse.value = it }
        }
    }
}