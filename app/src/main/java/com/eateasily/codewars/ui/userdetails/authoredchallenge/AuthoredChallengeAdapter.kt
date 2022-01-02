package com.eateasily.codewars.ui.userdetails.authoredchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eateasily.codewars.databinding.UiAuthoredChallengeItemBinding
import com.eateasily.codewars.models.AuthoredChallengeData

class AuthoredChallengeAdapter(private val listener: () -> AuthoredAdapterClickListener) : RecyclerView.Adapter<AuthoredChallengeAdapter.AuthoredViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<AuthoredChallengeData>(){
        override fun areItemsTheSame(oldItem: AuthoredChallengeData, newItem: AuthoredChallengeData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AuthoredChallengeData, newItem: AuthoredChallengeData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onBindViewHolder(holder: AuthoredViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthoredViewHolder {

        return AuthoredViewHolder(
            UiAuthoredChallengeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class AuthoredViewHolder(
        private val binding: UiAuthoredChallengeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AuthoredChallengeData?) {
            binding.txvName.text = data?.name
            binding.txvDesc.text = data?.description

            binding.root.setOnClickListener {
                listener.invoke().itemClicked(data!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}