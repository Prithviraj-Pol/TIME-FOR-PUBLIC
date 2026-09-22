package com.timeforpublic.feature.officer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.domain.model.OfficerStatus
import com.timeforpublic.domain.repository.OfficerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OfficerViewModel(
    private val officerRepository: OfficerRepository
) : ViewModel() {

    private val _officerStatus = MutableStateFlow<OfficerStatus?>(null)
    val officerStatus: StateFlow<OfficerStatus?> = _officerStatus.asStateFlow()

    private val _isGeofenceInside = MutableStateFlow(true)
    val isGeofenceInside: StateFlow<Boolean> = _isGeofenceInside.asStateFlow()

    init {
        loadOfficerProfile("OFF-101")
    }

    fun loadOfficerProfile(officerId: String) {
        viewModelScope.launch {
            when (val res = officerRepository.getOfficerById(officerId)) {
                is Result.Success -> _officerStatus.value = res.data
                else -> {}
            }
        }
    }

    fun updateStatus(status: AvailabilityStatus, note: String) {
        val current = _officerStatus.value ?: return
        viewModelScope.launch {
            when (val res = officerRepository.updateOfficerStatus(
                officerId = current.officerId,
                status = status,
                note = note,
                isGeofenceVerified = _isGeofenceInside.value
            )) {
                is Result.Success -> _officerStatus.value = res.data
                else -> {}
            }
        }
    }

    fun toggleGeofenceSimulation() {
        _isGeofenceInside.value = !_isGeofenceInside.value
    }
}
