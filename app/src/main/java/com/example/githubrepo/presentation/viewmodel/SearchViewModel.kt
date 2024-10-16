package com.example.githubrepo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepo.data.model.Repository
import com.example.githubrepo.data.repository.RepositoryImpl
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun searchRepositories(query: String, page: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.searchRepositories(query, page)
            if (result != null) {
                _repositories.value = result
            } else {
                _error.value = "Error loading repositories"
            }
            _loading.value = false
        }
    }
}