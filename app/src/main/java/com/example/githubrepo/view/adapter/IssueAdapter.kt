package com.example.githubrepo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepo.R
import com.example.githubrepo.data.model.Issue


class IssueAdapter(
    private var issues: List<Issue>
) : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_issue, parent, false)
        return IssueViewHolder(view)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(issues[position])
    }

    override fun getItemCount(): Int = issues.size

    fun updateData(newIssues: List<Issue>) {
        issues = newIssues
        notifyDataSetChanged()
    }

    inner class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val bodyTextView: TextView = itemView.findViewById(R.id.bodyTextView)

        fun bind(issue: Issue) {
            titleTextView.text = issue.title
            bodyTextView.text = issue.body ?: "No description available"
        }
    }
}