package com.example.githubrepo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.githubrepo.data.repository.RepositoryImpl
import com.example.githubrepo.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var repository: RepositoryImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = RepositoryImpl(RetrofitClient.api)

        lifecycleScope.launch {
            try {
                val repositories = repository.searchRepositories("android", 1)
                repositories?.forEach {
                    Log.d("MainActivity", "Repository: ${it.name}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Exception: ${e.message}")
            }
        }
    }
}