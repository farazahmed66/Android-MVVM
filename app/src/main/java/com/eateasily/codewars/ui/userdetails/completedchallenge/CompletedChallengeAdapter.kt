package com.eateasily.codewars.ui.userdetails.completedchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eateasily.codewars.databinding.ChallengeItemBinding
import com.eateasily.codewars.models.UserChallengeData

class CompletedChallengeAdapter(private val listener: () -> CompletedAdapterClickListener) :
    PagingDataAdapter<UserChallengeData, CompletedChallengeAdapter.ChallengeViewHolder>(
        ChallengeDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val data = getItem(position)

        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {

        return ChallengeViewHolder(
            ChallengeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class ChallengeViewHolder(
        private val binding: ChallengeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserChallengeData?) {

            binding.txvName.text = data?.name
            binding.txvCompleted.text = data?.completedAt
            binding.txvSlug.text = data?.slug

            binding.root.setOnClickListener {
                listener.invoke().itemClicked(data!!)
            }

        }
    }

    private class ChallengeDiffCallback : DiffUtil.ItemCallback<UserChallengeData>() {
        override fun areItemsTheSame(
            oldItem: UserChallengeData,
            newItem: UserChallengeData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserChallengeData,
            newItem: UserChallengeData
        ): Boolean {
            return oldItem == newItem
        }
    }

}