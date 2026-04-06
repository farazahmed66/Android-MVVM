package com.faraz.codewars.presentation.ui.userdetails.completedchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.faraz.codewars.databinding.ChallengeItemBinding
import com.faraz.codewars.domain.model.CompletedChallenge

class CompletedChallengeAdapter(private val listener: CompletedAdapterClickListener) :
    PagingDataAdapter<CompletedChallenge, CompletedChallengeAdapter.ChallengeViewHolder>(
        ChallengeDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        return ChallengeViewHolder(
            ChallengeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class ChallengeViewHolder(
        private val binding: ChallengeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CompletedChallenge?) {
            binding.txvName.text = data?.name
            binding.txvCompleted.text = data?.completedAt
            binding.txvSlug.text = data?.slug
            binding.root.setOnClickListener {
                data?.let { listener.itemClicked(it) }
            }
        }
    }

    private class ChallengeDiffCallback : DiffUtil.ItemCallback<CompletedChallenge>() {
        override fun areItemsTheSame(oldItem: CompletedChallenge, newItem: CompletedChallenge) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CompletedChallenge, newItem: CompletedChallenge) =
            oldItem == newItem
    }
}