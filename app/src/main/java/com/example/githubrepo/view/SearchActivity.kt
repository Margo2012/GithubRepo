package com.example.githubrepo.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepo.R
import com.example.githubrepo.data.model.Repository
import com.example.githubrepo.data.repository.RepositoryImpl
import com.example.githubrepo.network.RetrofitClient
import com.example.githubrepo.presentation.viewmodel.SearchViewModel
import com.example.githubrepo.presentation.viewmodel.SearchViewModelFactory
import com.example.githubrepo.view.adapter.RepositoryAdapter


class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(RepositoryImpl(RetrofitClient.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)

        setupRecyclerView()
        observeViewModel()

        viewModel.searchRepositories("android", 1)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RepositoryAdapter(emptyList()) { repository ->
            openDetailActivity(repository)
        }
    }

    private fun observeViewModel() {
        viewModel.repositories.observe(this) { repositories ->
            (recyclerView.adapter as RepositoryAdapter).updateData(repositories)
        }

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            errorTextView.visibility = if (error != null) View.VISIBLE else View.GONE
            errorTextView.text = error
        }
    }

    private fun openDetailActivity(repository: Repository) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("REPO_NAME", repository.full_name)
            putExtra("REPO_OWNER", repository.owner.avatar_url)
            putExtra("REPO_DESCRIPTION", repository.description)
        }
        startActivity(intent)
    }
}