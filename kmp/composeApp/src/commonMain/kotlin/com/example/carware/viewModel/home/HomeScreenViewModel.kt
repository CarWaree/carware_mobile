package com.example.carware.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carware.cache.reminderStore
import com.example.carware.cache.vehiclesStore
import com.example.carware.network.api.deleteVehicle
import com.example.carware.repository.VehicleRepository
import com.example.carware.util.storage.PreferencesManager
import com.example.carware.viewModel.profile.ProfileScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


class HomeScreenViewModel(
    private val repository: VehicleRepository,

) : ViewModel() {


    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    private val _nextReminderMillis = MutableStateFlow<Long?>(null)
    val nextReminderMillis: StateFlow<Long?> = _nextReminderMillis.asStateFlow()
    val cachedVehicles = vehiclesStore.updates
        .filterNotNull()
        .map { it.vehicles }

    init {
        loadVehicles()
        loadNextReminder()

    }


    fun loadVehicles() {
        viewModelScope.launch {
            if (_state.value !is HomeScreenState.Success) {
                _state.value = HomeScreenState.Loading
            }
            try {
                val vehicleList = repository.getVehiclesRepo()
                val appointmentsList = repository.getAppointmentsRepo()

                if (vehicleList.isEmpty()) {
                    _state.value = HomeScreenState.Error("No vehicles found.")
                } else {
                    _state.value = HomeScreenState.Success(vehicleList, appointmentsList)
                }
            } catch (e: Exception) {
                // ✅ Try cache before showing error
                val cached = vehiclesStore.get()
                if (cached != null && cached.vehicles.isNotEmpty()) {
                    _state.value = HomeScreenState.Success(
                        cached.vehicles,
                        emptyList() // no appointments cached
                    )
                } else {
                    _state.value = HomeScreenState.Error("No internet connection")
                }
            }
        }
    }
    private var currentCarId: Int? = null

    fun setCurrentCar(carId: Int) {
        currentCarId = carId
    }

    fun deleteCar() {
        val id = currentCarId ?: return
        viewModelScope.launch {
            repository.deleteVehicleRepo(id)
        }
    }

    @OptIn(ExperimentalTime::class)
    fun loadNextReminder() {
        viewModelScope.launch {
            val now = Clock.System.now().toEpochMilliseconds()
            val next = reminderStore.get()?.reminders
                ?.mapNotNull {
                    runCatching {
                        Instant.parse(it.notificationDate).toEpochMilliseconds()
                    }.getOrNull()
                }
                ?.filter { it > now }
                ?.minOrNull()
            _nextReminderMillis.update { next }
        }
    }
}