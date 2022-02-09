package com.example.basehelpers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basehelpers.data.model.SampleEntity
import com.example.basehelpers.data.repository.MainRepository
import com.example.basehelpers.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceUtil: SharedPreferenceUtil,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _someQueryLD =
        MutableLiveData<SampleEntity?>()
    val someQueryLD: LiveData<SampleEntity?>
        get() = _someQueryLD

    private val _insertQueryLD =
        MutableLiveData<SampleEntity?>()
    val insertQuery: LiveData<SampleEntity?>
        get() = _insertQueryLD

    private fun getSomeQuery() {
        viewModelScope.launch {
            val query = roomRepository.getSomeQuery()
            _someQueryLD.postValue(query)
        }
    }

    fun insertSomeQuery(sampleEntity: SampleEntity?) {
        viewModelScope.launch {
            if (sampleEntity != null) {
                Log.d("CHECK", "insertSomeQuery: $sampleEntity")
                roomRepository.insertSomeQuery(sampleEntity)
                getSomeQuery()
            }
        }
    }


}