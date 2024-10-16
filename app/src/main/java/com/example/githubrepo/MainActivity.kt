package com.example.githubrepo


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.githubrepo.data.repository.RepositoryImpl
import com.example.githubrepo.network.RetrofitClient
import com.example.githubrepo.presentation.viewmodel.SearchViewModel
import com.example.githubrepo.presentation.viewmodel.SearchViewModelFactory


class MainActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(RepositoryImpl(RetrofitClient.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.repositories.observe(this, Observer { repositories ->
            repositories.forEach {
                Log.d("MainActivity", "Repository: ${it.full_name}")
            }
        })

        viewModel.error.observe(this, Observer { error ->
            Log.e("MainActivity", "Error: $error")
        })

        viewModel.searchRepositories("android", 1)
    }
}