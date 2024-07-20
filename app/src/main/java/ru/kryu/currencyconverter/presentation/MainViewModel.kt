package ru.kryu.currencyconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kryu.currencyconverter.domain.ConvertRepository
import ru.kryu.currencyconverter.util.Resource
import ru.kryu.currencyconverter.util.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val convertRepository: ConvertRepository
) : ViewModel() {

    private val _screenState: MutableStateFlow<ConvertState> =
        MutableStateFlow(ConvertState.Content(0.0))
    val screenState: StateFlow<ConvertState>
        get() = _screenState
    private val _toastLiveData = SingleLiveEvent<String>()
    val toastLiveData: LiveData<String>
        get() = _toastLiveData

    fun convert(
        to: String,
        from: String,
        amount: Double
    ) {
        viewModelScope.launch {
            _screenState.value = ConvertState.Loading
            convertRepository.getConvertInfo(to = to, from = from, amount = amount)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _screenState.value = ConvertState.Error(resource.message ?: "Ошибка")
                            _screenState.value = ConvertState.Content(0.0)
                        }

                        is Resource.Success -> {
                            _screenState.value = ConvertState.Content(resource.data ?: 0.0)
                        }
                    }
                }
        }
    }
}