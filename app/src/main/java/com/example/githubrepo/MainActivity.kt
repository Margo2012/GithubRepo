package com.example.githubrepo

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.githubrepo.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.searchRepositories("android", 1)
                if (response.isSuccessful) {
                    response.body()?.items?.forEach {
                        Log.d("MainActivity", "Repository: ${it.full_name}")
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Exception: ${e.message}")
            }
        }
    }
}