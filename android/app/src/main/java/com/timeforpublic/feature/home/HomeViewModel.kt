package com.timeforpublic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentOffice
import com.timeforpublic.domain.model.GovernmentScheme
import com.timeforpublic.domain.model.OfficerStatus
import com.timeforpublic.domain.model.User
import com.timeforpublic.domain.repository.AuthRepository
import com.timeforpublic.domain.repository.OfficeRepository
import com.timeforpublic.domain.repository.OfficerRepository
import com.timeforpublic.domain.repository.SchemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val user: User? = null,
    val liveOfficers: List<OfficerStatus> = emptyList(),
    val featuredSchemes: List<GovernmentScheme> = emptyList(),
    val offices: List<GovernmentOffice> = emptyList(),
    val isLoading: Boolean = true,
    val searchQuery: String = ""
)

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val officerRepository: OfficerRepository,
    private val schemeRepository: SchemeRepository,
    private val officeRepository: OfficeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Current user
            val currentUser = authRepository.getCurrentUser()

            // Collect live officers
            officerRepository.getAllLiveOfficers().collect { result ->
                if (result is Result.Success) {
                    _uiState.value = _uiState.value.copy(
                        user = currentUser,
                        liveOfficers = result.data,
                        isLoading = false
                    )
                }
            }
        }

        viewModelScope.launch {
            schemeRepository.getSchemes().collect { result ->
                if (result is Result.Success) {
                    _uiState.value = _uiState.value.copy(featuredSchemes = result.data)
                }
            }
        }

        viewModelScope.launch {
            officeRepository.getOffices().collect { result ->
                if (result is Result.Success) {
                    _uiState.value = _uiState.value.copy(offices = result.data)
                }
            }
        }
    }

    fun onSearchChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLoggedOut()
        }
    }
}
