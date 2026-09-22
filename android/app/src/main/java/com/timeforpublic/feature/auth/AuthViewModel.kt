package com.timeforpublic.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.User
import com.timeforpublic.domain.model.UserRole
import com.timeforpublic.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: User) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _selectedRole = MutableStateFlow(UserRole.CITIZEN)
    val selectedRole: StateFlow<UserRole> = _selectedRole.asStateFlow()

    fun setRole(role: UserRole) {
        _selectedRole.value = role
    }

    fun login(phone: String) {
        if (phone.length < 10) {
            _uiState.value = AuthUiState.Error("Please enter a valid 10-digit mobile number")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            when (val result = authRepository.login(phone, _selectedRole.value)) {
                is Result.Success -> _uiState.value = AuthUiState.Success(result.data)
                is Result.Error -> _uiState.value = AuthUiState.Error(result.message)
                else -> {}
            }
        }
    }
}
