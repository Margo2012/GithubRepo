package com.example.githubrepo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepo.data.model.Issue
import com.example.githubrepo.data.repository.RepositoryImpl
import kotlinx.coroutines.launch



class DetailViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val _issues = MutableLiveData<List<Issue>>()
    val issues: LiveData<List<Issue>> get() = _issues

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun getIssues(owner: String, repo: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val result = repository.getIssues(owner, repo)
            if (result != null) {
                _issues.value = result
            } else {
                _error.value = "Error loading issues"
            }
            _loading.value = false
        }
    }
}