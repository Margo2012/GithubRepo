package com.example.githubrepo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepo.R
import com.example.githubrepo.data.model.Repository
import com.squareup.picasso.Picasso

class RepositoryAdapter(
    private var repositories: List<Repository>,
    private val onItemClick: (Repository) -> Unit
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    fun updateData(newRepositories: List<Repository>) {
        repositories = newRepositories
        notifyDataSetChanged()
    }

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(repository: Repository) {
            nameTextView.text = repository.full_name
            descriptionTextView.text = repository.description
            Picasso.get().load(repository.owner.avatar_url).into(avatarImageView)

            itemView.setOnClickListener {
                onItemClick(repository)
            }
        }
    }
}

