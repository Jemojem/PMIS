package com.example.a5_6lab

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.a5_6lab.db.MainDb
import com.example.a5_6lab.utils.ListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
@HiltViewModel
class MainViewModel @Inject constructor(
    val mainDb: MainDb
):ViewModel() {
    val mainList = mutableStateOf(emptyList<ListItem>())
    private var job: Job? = null
    fun getAllItemsByCategory(cat: String) {
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getAllItemsByCategory(cat).collect { list -> mainList.value =
                list }
        }
    }
    fun getFavorites(){
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getFavorites().collect{ list -> mainList.value = list}
        }
    }
    fun insertItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.insertItem(item)
    }
    fun deleteItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.deleteItem(item)
    }
}