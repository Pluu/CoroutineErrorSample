package com.pluu.samplecoroutinetest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pluu.samplecoroutinetest.api.ApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val che = CoroutineExceptionHandler { _, _ ->
        errorEvent.value = "error"
    }

    val p1Event = MutableLiveData<String>()
    val errorEvent = MutableLiveData<String>()

    fun ping() {
        viewModelScope.launch(che) {
            withContext(Dispatchers.IO) {
                val list = apiService.contributors(
                    owner = "Pluu",
                    repo = "WebToon"
                )
                p1Event.value = list.joinToString {
                    it.login
                }
            }
        }
    }
}