package com.example.githubrepo.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepo.R
import com.example.githubrepo.data.repository.RepositoryImpl
import com.example.githubrepo.network.RetrofitClient
import com.example.githubrepo.presentation.viewmodel.DetailViewModel
import com.example.githubrepo.presentation.viewmodel.DetailViewModelFactory
import com.example.githubrepo.view.adapter.IssueAdapter


class DetailActivity : AppCompatActivity() {

    private lateinit var repoNameTextView: TextView
    private lateinit var repoDescriptionTextView: TextView
    private lateinit var issuesRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(RepositoryImpl(RetrofitClient.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        repoNameTextView = findViewById(R.id.repoName)
        repoDescriptionTextView = findViewById(R.id.repoDescription)
        issuesRecyclerView = findViewById(R.id.issuesRecyclerView)
        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)

        setupUI()
        setupRecyclerView()
        observeViewModel()

        val repoName = intent.getStringExtra("REPO_NAME") ?: ""
        val repoOwner = intent.getStringExtra("REPO_OWNER") ?: ""
        viewModel.getIssues(repoOwner, repoName)
    }

    private fun setupUI() {
        repoNameTextView.text = intent.getStringExtra("REPO_NAME")
        repoDescriptionTextView.text = intent.getStringExtra("REPO_DESCRIPTION")
    }

    private fun setupRecyclerView() {
        issuesRecyclerView.layoutManager = LinearLayoutManager(this)
        issuesRecyclerView.adapter = IssueAdapter(emptyList())
    }

    private fun observeViewModel() {
        viewModel.issues.observe(this) { issues ->
            (issuesRecyclerView.adapter as IssueAdapter).updateData(issues)
        }

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            errorTextView.visibility = if (error != null) View.VISIBLE else View.GONE
            errorTextView.text = error
        }
    }
}