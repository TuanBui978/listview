package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiState(val num: Int = 0, val list: MutableList<Int> = mutableListOf())

class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun insertNum() {
        _uiState.update {
            val newList = it.list
            newList.add(it.num)
            it.copy(num= it.num + 1, newList)
        }
    }

    fun desertNum() {
        _uiState.update {
            val newList = it.list
            newList.add(it.num)
            it.copy(num= it.num - 1, newList)
        }
    }

    fun updateList(value: Int, index: Int) {
        _uiState.update {
            val newList = it.list
            newList[index] = value
            it.copy(list = newList)
        }
    }

    fun deleteList(index: Int) {
        _uiState.update {
            val newList = it.list
            newList.removeAt(index)
            it.copy(list = newList)
        }
    }


}